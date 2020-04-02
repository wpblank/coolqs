package pub.izumi.coolqs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;


public class UserTest {

    @Test
    public void UserTest() {
        User user0 = new User(1,"izumi");
        User user1 = new User.Builder(1,"izumi").build();
        User user2 = new User.Builder(1,"izumi").sex("男").phone("保密").build();
        String a = JSON.toJSONString(user0);
        System.out.println(a);
        System.out.println(JSONObject.toJSONString(user0));
        System.out.println(JSON.toJSONString(user2));
        System.out.println(JSONObject.toJSONString(user2));
    }
}
