package pub.izumi.coolqs.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.Message;

/**
 * @author izumi
 */
@Mapper
@Component
public interface MessageMapper extends BaseMapper<Message> {

}
