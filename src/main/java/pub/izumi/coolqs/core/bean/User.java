package pub.izumi.coolqs.core.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author izumi
 */
@Data
public class User {
    @TableId
    private long userId;
    private String nickname;
    private String avatar;
    private Long groupId;
    private Integer status;

    public User(long userId, String nickname, String avatar, Long groupId, Integer status) {
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = "https://q1.qlogo.cn/g?b=qq&nk=" + userId + "&s=640";
        this.groupId = groupId;
        this.status = status;
    }

}
