package pub.izumi.coolqs.core;

import com.alibaba.fastjson.JSON;
import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.bean.MessageGroup;
import pub.izumi.coolqs.core.bean.User;
import pub.izumi.coolqs.core.mapper.MessageGroupMapper;
import pub.izumi.coolqs.core.mapper.MessageMapper;
import pub.izumi.coolqs.core.mapper.UserMapper;
import pub.izumi.coolqs.elehb.ElehbService;
import pub.izumi.coolqs.weather.WeatherService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pyr
 * <p>
 * 添加事件：光标移动到类中，按 Ctrl+O 添加事件(讨论组消息、加群请求、加好友请求等)
 * 查看API参数类型：光标移动到方法括号中按Ctrl+P
 * 查看API说明：光标移动到方法括号中按Ctrl+Q
 * <p>
 * 统一消息接收处理中心
 */
@Service
public class MsgCenter extends CQPlugin {

    @Autowired
    MessageGroupMapper messageGroupMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    WeatherService weatherService;
    @Autowired
    ElehbService elehbService;

    @Value("${pub.izumi.elemeStarUrl}")
    private String elemeStarUrl;
    @Value("${pub.izumi.debug}")
    private Boolean debug;

    private static final Logger logger = LoggerFactory.getLogger(MsgCenter.class);

    List<MessageGroup> messageGroups = new ArrayList<>();
    /**
     * 保存群聊数据批量插入的最大条数
     */
    private static final int BATCH_SIZE = 30;

    /**
     * 收到私聊消息时会调用这个方法
     *
     * @param cq    机器人对象，用于调用API，例如发送私聊消息 sendPrivateMsg
     * @param event 事件对象，用于获取消息内容、群号、发送者QQ等
     * @return 是否继续调用下一个插件，IGNORE表示继续，BLOCK表示不继续
     */
    @Override
    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {

        // 获取 发送者QQ 和 消息内容
        long userId = event.getUserId();
        String message = event.getMessage();
        Message msg = new Message(event.getSubType(), event.getMessageType(), userId, event.getSender().getNickname(),
                message, event.getPostType());
        switch (message) {
            case "在线帮助":
                msg.setResponse("感谢使用！");
                return response(cq, msg);
            default:
                break;
        }
        if (message.contains(elemeStarUrl)) {
            return response(cq, elehbService.getEleHb(msg));
        }
        msg.setResponse(msg.getMsg());
        return response(cq, msg);
    }


    /**
     * 收到群消息时会调用这个方法
     *
     * @param cq    机器人对象，用于调用API，例如发送群消息 sendGroupMsg
     * @param event 事件对象，用于获取消息内容、群号、发送者QQ等
     * @return 是否继续调用下一个插件，IGNORE表示继续，BLOCK表示不继续
     */
    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String message = event.getMessage();
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        MessageGroup messageGroup = new MessageGroup(event.getSubType(), event.getMessageType(), userId,
                event.getSender().getNickname(), message, event.getPostType(),
                event.getSender().getAge(), event.getSender().getSex(), event.getSender().getLevel(), event.getSender().getRole(), groupId);

        // 被 @事件
        if (message.contains(CQCode.at(event.getSelfId()))) {
            if (message.contains("取消天气提醒")) {
                User user = new User(userId, messageGroup.getNickname(), groupId, 1);
                messageGroup.setResponse(CQCode.at(userId) + weatherService.freezeUser(user));
                response(cq, messageGroup);
            } else if (message.contains("天气提醒")) {
                User user = new User(userId, messageGroup.getNickname(), groupId, 1);
                messageGroup.setResponse(CQCode.at(userId) + weatherService.activateUser(user));
                response(cq, messageGroup);
            }
        }
        return ignore(messageGroup);

    }

    public void saveMsg(Message msg) {
        if (debug) {
            return;
        }
        if (messageMapper.insert(msg) <= 0) {
            logger.error("保存数据库失败{}", JSON.toJSONString(msg));
        }
    }

    public void saveMsg(MessageGroup msg) {
        if (debug) {
            return;
        }
        messageGroups.add(msg);
        if (messageGroups.size() > BATCH_SIZE) {
            List<MessageGroup> tempMsgList = messageGroups.subList(0, BATCH_SIZE);
            if (messageGroupMapper.insertBatch(tempMsgList) == BATCH_SIZE) {
                messageGroups.removeAll(tempMsgList);
            } else {
                logger.error("保存数据库失败,{}", messageGroups.size());
            }
        }
    }

    public void saveMsgWhenExit() {
        if (debug) {
            return;
        }
        logger.info("程序关闭,保存剩余{}条记录", messageGroupMapper.insertBatch(messageGroups));
    }

    /**
     * 回复群聊信息
     */
    public int response(CoolQ cq, MessageGroup messageGroup) {
        saveMsg(messageGroup);
        cq.sendGroupMsg(messageGroup.getGroupId(), messageGroup.getResponse(), false);
        return MESSAGE_BLOCK;
    }

    /**
     * 回复私聊信息
     */
    public int response(CoolQ cq, Message message) {
        saveMsg(message);
        cq.sendPrivateMsg(message.getUserId(), message.getResponse(), false);
        // 不执行下一个插件
        return MESSAGE_BLOCK;
    }

    public int ignore(Message message) {
        saveMsg(message);
        return MESSAGE_IGNORE;
    }

    public int ignore(MessageGroup messageGroup) {
        saveMsg(messageGroup);
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
    }
}
