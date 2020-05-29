package pub.izumi.coolqs.core.service.websocket;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.service.ChatService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author izumi
 */
@ServerEndpoint("/websocket/chat/{id}")
@Component
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * <key,Set<WebSocketServer>>
     */
    private static ConcurrentHashMap<Long, Set<WebSocketServer>> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private Long id;
    /**
     * 客户端的唯一标识符
     */
    private String clientId = UUID.randomUUID().toString();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") Long id) {
        this.session = session;
        log.info(session.getId());
        this.id = id;
        if (!getWebSocketMap().containsKey(this.id)) {
            getWebSocketMap().put(this.id, new HashSet<>());
        }
        getWebSocketMap().get(this.id).add(this);
        addOnlineCount();
        log.info("用户{}连接:" + this.id + ",当前在线人数为:" + getOnlineCount(), clientId);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + this.id + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (getWebSocketMap().containsKey(id)) {
            getWebSocketMap().get(id).remove(this);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户{}退出:" + id + ",当前在线人数为:" + getOnlineCount(), clientId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("用户消息:" + id + ",报文:" + message);
        // 可以群发消息
        // 消息保存到数据库、redis
//        if (StringUtils.isNotBlank(message)) {
//            try {
//                //解析发送的报文
//                JSONObject jsonObject = JSON.parseObject(message);
//                //追加发送人(防止串改)
//                jsonObject.put("fromUserId", this.vehNo);
//                String toUserId = jsonObject.getString("toUserId");
//                //传送给对应toUserId用户的websocket
//                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
//                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
//                } else {
//                    log.error("请求的userId:" + toUserId + "不在该服务器上");
//                    //否则不在这个服务器上，发送到mysql或者redis
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        sendInfo(id, JSON.toJSONString(ChatService.chatLinkedList));
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.id + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(Long key, String message) throws IOException {
//        log.info("发送消息到:" + lineId + "，报文:" + message);
        if (key != null && getWebSocketMap().containsKey(key) && getWebSocketMap().get(key).size() > 0) {
            log.debug("发送{}消息，在线人数：{}", key, getWebSocketMap().get(key).size());
            for (WebSocketServer webSocketServer : getWebSocketMap().get(key)) {
                webSocketServer.sendMessage(message);
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static synchronized ConcurrentHashMap<Long, Set<WebSocketServer>> getWebSocketMap() {
        return webSocketMap;
    }

    @Override
    public int hashCode() {
        return clientId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == String.class) {
            return obj.equals(clientId);
        }
        return false;
    }
}
