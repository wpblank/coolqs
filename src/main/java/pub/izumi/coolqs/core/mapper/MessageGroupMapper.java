package pub.izumi.coolqs.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.MessageGroup;

/**
 * @author izumi
 */
@Mapper
@Component
public interface MessageGroupMapper extends BaseMapper<MessageGroup> {

}
