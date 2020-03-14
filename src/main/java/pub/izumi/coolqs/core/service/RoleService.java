package pub.izumi.coolqs.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.lz1998.cq.utils.CQCode;
import pub.izumi.coolqs.core.bean.UserRole;
import pub.izumi.coolqs.core.mapper.UserRoleMapper;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    public String getAtqqlistById(int id) {
        List<UserRole> userRoles = userRoleMapper.getUserRoleListById(id);
        StringBuilder atqqlist = new StringBuilder();
        for (UserRole userRole : userRoles) {
            atqqlist.append(CQCode.at(userRole.getUserId()));
        }
        return atqqlist.toString();
    }
}
