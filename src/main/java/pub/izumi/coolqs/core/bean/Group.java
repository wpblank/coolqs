package pub.izumi.coolqs.core.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author izumi
 */
@Data
public class Group {
    @TableId
    private long groupId;
    private String groupName;
    private String avatar;
    /**
     * 0：收进群助手 1：正常接收消息
     */
    private Integer status;

    public Group(long groupId, String groupName, String avatar, Integer status) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.avatar = "https://p.qlogo.cn/gh/" + groupId + "/" + groupId + "/640";
        this.status = status;
    }
}
