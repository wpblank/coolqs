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
    private Long groupId;
    private Integer status;

    public User(long userId, String nickname, Long groupId,Integer status) {
        this.userId = userId;
        this.nickname = nickname;
        this.groupId = groupId;
        this.status = status;
    }

}
