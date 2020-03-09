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
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.bean.MessageGroup;
import pub.izumi.coolqs.core.mapper.MessageGroupMapper;
import pub.izumi.coolqs.core.mapper.MessageMapper;

/**
 * @author pyr
 * <p>
 * 添加事件：光标移动到类中，按 Ctrl+O 添加事件(讨论组消息、加群请求、加好友请求等)
 * 查看API参数类型：光标移动到方法括号中按Ctrl+P
 * 查看API说明：光标移动到方法括号中按Ctrl+Q
 */
@Component
public class MsgCenter extends CQPlugin {

    @Autowired
    MessageGroupMapper messageGroupMapper;
    @Autowired
    MessageMapper messageMapper;

    private static final Logger logger = LoggerFactory.getLogger(MsgCenter.class);

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
        // 调用API发送hello
        // cq.sendPrivateMsg(userId, message, false);
        switch (message) {
            case "在线帮助":
                msg.setResponse("感谢使用！");
                return response(msg);
            default:
                break;
        }
//        if (msgStr.contains(eleUrl)) {
//            ElehbService elehbService = new ElehbService();
//            return response(elehbService.getEleHb(msg));
//        }
        msg.setResponse(msg.getMsg());
        return response(msg);
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
        // 获取 消息内容 群号 发送者QQ
        String msg = event.getMessage();
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        MessageGroup messageGroup = new MessageGroup(event.getSubType(), event.getMessageType(), userId,
                event.getSender().getNickname(), msg, event.getPostType(), event.getSender().getAge(),
                event.getSender().getSex(), event.getSender().getLevel(), event.getSender().getRole(), groupId);

//            // 回复内容为 at发送者 + hi
//            String result = CQCode.at(userId) + "hi";
//            // 调用API发送消息
//            cq.sendGroupMsg(groupId, result, false);
        return ignore(messageGroup);

    }

    public void saveMsg(Message msg) {
        messageMapper.insert(msg);
    }

    public void saveMsg(MessageGroup msg) {
        messageGroupMapper.insert(msg);
    }

    public int response(MessageGroup messageGroup) {
        saveMsg(messageGroup);
        // 不执行下一个插件
        return MESSAGE_BLOCK;
    }

    public int response(Message message) {
        saveMsg(message);
        // 不执行下一个插件
        return MESSAGE_BLOCK;
    }

    public int ignore(Message message) {
        saveMsg(message);
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
    }

    public int ignore(MessageGroup messageGroup) {
        saveMsg(messageGroup);
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
    }
}
