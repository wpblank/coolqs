package pub.izumi.coolqs.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.bean.Message;

/**
 * @author izumi
 */
@Mapper
@Component
public interface MessageMapper extends BaseMapper<Message> {

}
