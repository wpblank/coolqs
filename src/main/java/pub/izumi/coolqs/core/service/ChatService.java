package pub.izumi.coolqs.core.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.izumi.coolqs.core.bean.Chat;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.mapper.ChatMapper;
import pub.izumi.coolqs.core.service.websocket.WebSocketServer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;


/**
 * @author izumi
 */
@Log4j2
@Service
public class ChatService {
    @Autowired
    ChatMapper chatMapper;

    /**
     * Chat对象列表，按 新==>旧 排序
     */
    public static LinkedList<Chat> chatLinkedList = new LinkedList<>();

    @PostConstruct
    public void init() {
        log.info("----------初始化聊天列表----------");
        chatLinkedList.addAll(chatMapper.selectList(new QueryWrapper<Chat>().orderByDesc("update_time")));
        log.info("----------初始化聊天列表成功，个数：{}----------", chatLinkedList.size());
    }

    public static void onPrivateMessage(Message message) {
        int idx = 0;
        // 查找接收到的消息位于列表中的位置
        for (int i = 0; i < chatLinkedList.size(); i++) {
            Chat chat = chatLinkedList.get(i);
            if (chat.getId().equals(message.getUserId())) {
                idx = i;
                if (idx <= 5) {
                    updateChat(chat, message);
                } else {
                    // 将记录位置置顶
                    Chat temp = chatLinkedList.remove(idx);
                    updateChat(temp, message);
                    chatLinkedList.push(temp);
                }
                pushChat(chat, idx);
                return;
            }
        }
        // 消息不在聊天记录列表中
        idx = -1;
        Chat chat = new Chat(message.getUserId(), message.getNickname(), "",
                message.getMsg(), 1, message.getCreateTime());
        chatLinkedList.push(chat);
        pushChat(chat, idx);
    }

    /**
     * 更新聊天列表字段
     */
    private static void updateChat(Chat chat, Message message) {
        chat.setLastMessage(message.getMsg());
        chat.setName(message.getNickname());
        chat.setUpdateTime(message.getCreateTime());
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
