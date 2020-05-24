package pub.izumi.coolqs.core.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Chat {
    @TableId
    Long id;
    String name;
    String avatar;
    String lastMessage;
}
