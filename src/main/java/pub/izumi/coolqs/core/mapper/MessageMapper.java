package pub.izumi.coolqs.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.Message;

import java.util.List;

/**
 * @author izumi
 */
@Mapper
@Component
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT * FROM `message` WHERE user_id = #{qq} ORDER BY id DESC")
    List<Message> getMessageList(String qq);
}
