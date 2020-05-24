package pub.izumi.coolqs.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.Chat;
import pub.izumi.coolqs.core.bean.MessageGroup;

/**
 * @author izumi
 */
@Mapper
@Component
public interface ChatMapper extends BaseMapper<Chat> {
}
