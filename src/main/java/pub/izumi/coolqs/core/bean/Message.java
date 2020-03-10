package pub.izumi.coolqs.core.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author izumi
 */
@Data
public class Message {
    @TableId
    private int id;
    /**
     * 子类型
     */
    private String subType;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 来源QQ
     */
    private long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 消息内容
     */
    private String msg;
    private String postType;
    /**
     * 响应消息
     */
    private String response;
    private Timestamp createTime;

    public Message() {
    }

    public Message(String subType, String messageType, long userId, String nickname, String msg, String postType,Timestamp createTime) {
        this.subType = subType;
        this.messageType = messageType;
        this.userId = userId;
        this.nickname = nickname;
        this.msg = msg;
        this.postType = postType;
        this.createTime = createTime;
    }
}
