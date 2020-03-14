package pub.izumi.coolqs.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.User;

/**
 * @author izumi
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

    @Insert("REPLACE INTO user(user_id,nickname,group_id) VALUES (#{userId},#{nickname},#{groupId})")
    int repalceUser(User user);

    @Insert("REPLACE INTO user(user_id,nickname,group_id,status) VALUES (#{userId},#{nickname},#{groupId},#{status})")
    int freezeUser(User user);
}
