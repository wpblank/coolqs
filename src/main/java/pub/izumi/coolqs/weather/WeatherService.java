package pub.izumi.coolqs.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.izumi.coolqs.core.bean.User;
import pub.izumi.coolqs.core.bean.UserRole;
import pub.izumi.coolqs.core.mapper.UserMapper;
import pub.izumi.coolqs.core.mapper.UserRoleMapper;

import static pub.izumi.coolqs.core.config.Constant.*;


@Service
public class WeatherService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    public String activateUser(User user) {
        userMapper.repalceUser(user);
        UserRole userRole = new UserRole(user.getUserId(), weatherRole, 0, 1);
        Integer integer = userRoleMapper.getStatus(userRole);
        if (integer == null) {
            userRoleMapper.insert(userRole);
            return "已添加天气提醒";
        } else if (integer.equals(freeze)) {
            userRoleMapper.activateUserRole(userRole);
            return "已添加天气提醒";
        } else if (integer.equals(activation)) {
            return "已在天气提醒列表中";
        } else {
            return "未知错误";
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String freezeUser(User user) {
        userMapper.repalceUser(user);
        UserRole userRole = new UserRole(user.getUserId(), weatherRole, 0, 1);
        Integer integer = userRoleMapper.getStatus(userRole);
        if (integer == null) {
            userRoleMapper.insert(userRole);
            return "未在天气提醒列表中";
        } else if (integer.equals(freeze)) {
            return "未在天气提醒列表中";
        } else if (integer.equals(activation)) {
            userRoleMapper.freezeUserRole(userRole);
            return "已取消天气提醒";
        } else {
            return "未知错误";
        }
    }

}
