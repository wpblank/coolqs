package pub.izumi.coolqs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import static pub.izumi.coolqs.core.utils.PicUtils.downImage;

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

    @Test
    public void Test() {
        downImage("https://gchat.qpic.cn/gchatpic_new/710426694/617166836-3117329906-6685375A8AAC43108DF531BD055997C2/0?term=2",
                "ceshi.jpg","/Users/izumi/Downloads/");
    }
}
