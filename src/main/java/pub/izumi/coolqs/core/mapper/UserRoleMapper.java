package pub.izumi.coolqs.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.UserRole;

import java.util.List;

@Mapper
@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("select status from user_role where user_id=#{userId} and id=#{id}")
    Integer getStatus(UserRole userRole);

    /**
     * 激活账号某个权限
     */
    @Update("update user_role set status=1 where user_id=#{userId} and id=#{id}")
    int activateUserRole(UserRole userRole);

    /**
     * 冻结账号某个权限
     */
    @Update("update user_role set status=0 where user_id=#{userId} and id=#{id}")
    int freezeUserRole(UserRole userRole);

    /**
     * 查询拥有某权限的全部账号
     */
    @Select("select * from user_role where id=#{id} and status=1")
    List<UserRole> getUserRoleListById(int id);

}
