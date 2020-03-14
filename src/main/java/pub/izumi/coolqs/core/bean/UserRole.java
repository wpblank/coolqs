package pub.izumi.coolqs.core.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserRole {
    @TableId
    private long userId;
    @TableId
    private int id;
    private int amount;
    private int margin;
    private int totalUse;
    private Integer status;

    public UserRole(long userId, int id, int amount, Integer status) {
        this.userId = userId;
        this.id = id;
        this.amount = amount;
        this.margin = 0;
        this.totalUse = 0;
        this.status = status;
    }

    public UserRole(long userId, int id, int amount, int margin, int totalUse, Integer status) {
        this.userId = userId;
        this.id = id;
        this.amount = amount;
        this.margin = margin;
        this.totalUse = totalUse;
        this.status = status;
    }
}
