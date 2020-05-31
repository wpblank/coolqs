package pub.izumi.coolqs.core.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import net.lz1998.cq.robot.CoolQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.izumi.coolqs.core.bean.Chat;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.mapper.ChatMapper;
import pub.izumi.coolqs.core.mapper.MessageGroupMapper;
import pub.izumi.coolqs.core.mapper.MessageMapper;
import pub.izumi.coolqs.core.service.websocket.WebSocketServer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static net.lz1998.cq.CQGlobal.robots;


/**
 * @author izumi
 */
@Log4j2
@Service
public class ChatService {
    @Autowired
    ChatMapper chatMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageGroupMapper messageGroupMapper;

    /**
     * Chat对象列表，按 新==>旧 排序
     */
    public static LinkedList<Chat> chatLinkedList = new LinkedList<>();
    public static String msgTableName = "";
    public static String msgGroupTableName = "";

    @PostConstruct
    public void init() {
        log.info("----------初始化聊天列表----------");
        chatLinkedList.addAll(chatMapper.selectList(new QueryWrapper<Chat>().orderByDesc("update_time")));
        log.info("----------初始化聊天列表成功，个数：{}----------", chatLinkedList.size());
        new Thread(() -> {
            while (true) {
                if (robots.values().iterator().hasNext()) {
                    log.info("----------初始化聊天记录表----------");
                    CoolQ coolQ = robots.values().iterator().next();
                    msgTableName = "message_" + coolQ.getSelfId();
                    if (messageMapper.hasTable(msgTableName) == 0) {
                        messageMapper.createTable(msgTableName);
                        log.info("----------创建表:{}----------", msgTableName);
                    }
                    msgGroupTableName = "message_group_" + coolQ.getSelfId();
                    if (messageGroupMapper.hasTable(msgGroupTableName) == 0) {
                        messageGroupMapper.createTable(msgGroupTableName);
                        log.info("----------创建表:{}----------", msgGroupTableName);
                    }
                    break;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }

    public static void onMessage(Chat newChat) {
        int idx = 0;
        // 查找接收到的消息位于列表中的位置
        for (int i = 0; i < chatLinkedList.size(); i++) {
            Chat chat = chatLinkedList.get(i);
            if (chat.getId().equals(newChat.getId())) {
                idx = i;
                if (idx <= 5) {
                    updateChat(chat, newChat);
                } else {
                    // 将记录位置置顶
                    chat = chatLinkedList.remove(idx);
                    updateChat(chat, newChat);
                    chatLinkedList.push(chat);
                }
                pushChat(chat, idx);
                return;
            }
        }
        // 消息不在聊天记录列表中
        idx = -1;
        chatLinkedList.push(newChat);
        pushChat(newChat, idx);
    }

    /**
     * 更新聊天列表字段
     */
    private static void updateChat(Chat chat, Chat newChat) {
        chat.setLastMessage(newChat.getLastMessage());
        chat.setName(newChat.getName());
        chat.setUpdateTime(newChat.getUpdateTime());
        chat.addLastMessage();
    }

    private static void pushChat(Chat chat, int idx) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idx", idx);
        jsonObject.put("chat", chat);
        try {
            WebSocketServer.sendInfo(chat.getId(), jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
