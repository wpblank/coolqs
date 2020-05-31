package pub.izumi.coolqs.core.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.bean.MessageGroup;
import pub.izumi.coolqs.core.bean.User;
import pub.izumi.coolqs.core.mapper.UserMapper;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author izumi
 */
@Log4j2
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public static List<User> userList = new ArrayList<>();
    /**
     * <qq,user>
     */
    public static Map<Long, User> userMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("----------初始化用户列表----------");
        userList = userMapper.selectList(null);
        userList.forEach(user -> userMap.put(user.getUserId(), user));
        log.info("----------初始化用户列表成功，个数{}----------", userList.size());
    }

    public static void addUser(User user) {
        if (!userMap.containsKey(user.getUserId())) {
            userList.add(user);
            userMap.put(user.getUserId(), user);
        }
    }

    public static void deleteUser(Long userId) {
        if (userMap.containsKey(userId)) {
            userList.remove(userMap.remove(userId));
        }
    }
}
