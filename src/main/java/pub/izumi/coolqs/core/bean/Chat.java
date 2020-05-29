package pub.izumi.coolqs.core.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author izumi
 * 聊天列表对象
 */
@Data
public class Chat {
    @TableId
    private Long id;
    private String name;
    private String avatar;
    private String lastMessage;
    private int unreadMessage;
    private Timestamp updateTime;

    public Chat() {
    }

    public Chat(Long id, String name, String avatar, String lastMessage, int unreadMessage, Timestamp updateTime) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.lastMessage = lastMessage;
        this.unreadMessage = unreadMessage;
        this.updateTime = updateTime;
    }
}
