package pub.izumi.coolqs.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author izumi
 */
@Data
public class Message {
    /**
     * 子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组
     */
    private int subType;
    /**
     * 消息ID
     */
    private int msgId;
    /**
     * 来源QQ
     */
    private long fromqq;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 字体
     */
    private int font;
    /**
     * 响应消息
     */
    private String response;
}
