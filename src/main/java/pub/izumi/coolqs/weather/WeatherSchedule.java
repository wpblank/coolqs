package pub.izumi.coolqs.weather;

import com.alibaba.fastjson.JSONObject;
import net.lz1998.cq.robot.CoolQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pub.izumi.coolqs.core.MsgCenter;
import pub.izumi.coolqs.core.bean.MessageGroup;

import java.util.HashMap;
import java.util.Map;

import static net.lz1998.cq.CQGlobal.robots;

/**
 * @author izumi
 * 定时任务，每日雨雪天气警报
 */
@Component
public class WeatherSchedule {
    @Autowired
    MsgCenter msgCenter;

    @Value("${pub.izumi.weather.caiYunUrl}")
    private String caiYunUrl;
    @Value("${pub.izumi.weather.caiYunKey}")
    private String caiYunKey;
    @Value("${pub.izumi.weather.caiYunLocation}")
    private String caiYunLocation;

    private static final Logger logger = LoggerFactory.getLogger(WeatherSchedule.class);
    private RestTemplate restTemplate = new RestTemplate();
//    private MessageGroup messageGroup = new MessageGroup();

    public WeatherSchedule() {
        logger.info("-----开始天气监控-----");
        // messageGroup.set
    }

    /**
     * 请求彩云天气 "小时级预报接口"，当天有雨时qq预警。
     */
    @Scheduled(cron = "0 3 */1 * * ?")
    public void getWeather() {
        if (robots.values().iterator().hasNext()) {
            CoolQ coolQ = robots.values().iterator().next();
            logger.info(coolQ.toString());
            // coolQ.sendPrivateMsg(qq, "消息", false);
            String url = caiYunUrl + caiYunKey + caiYunLocation;
            Map<String, Object> param = new HashMap<>();
            param.put("lang", "zh_CN");
            param.put("hourlysteps", 1);
            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, null, String.class, param);
            JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
            jsonObject = jsonObject.getJSONObject("result").getJSONObject("hourly");
            logger.info(jsonObject.toJSONString());
//        CQ.sendPrivateMsg(qq, JSON.toJSONString(responseEntity.getBody()));
//        CQ.sendGroupMsg(qq, JSON.toJSONString(responseEntity.getBody()));
        }

    }

//    @Scheduled(cron = "01 00 00 * * ?")
//    public void task() {
//        cq.sendPrivateMsg(qq, "消息", false);
//    }


}
