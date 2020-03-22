package pub.izumi.coolqs.weather;

import com.alibaba.fastjson.JSONArray;
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
import org.springframework.web.client.RestTemplate;
import pub.izumi.coolqs.core.MsgCenter;
import pub.izumi.coolqs.core.bean.MessageGroup;
import pub.izumi.coolqs.core.service.RoleService;

import java.util.*;

import static net.lz1998.cq.CQGlobal.robots;
import static pub.izumi.coolqs.core.config.Constant.weatherRole;

/**
 * @author izumi
 * 定时任务，每日雨雪天气警报
 */
@Component
public class WeatherSchedule {
    @Autowired
    MsgCenter msgCenter;
    @Autowired
    RoleService roleService;

    @Value("${pub.izumi.weather.caiYunUrl}")
    private String caiYunUrl;
    @Value("${pub.izumi.weather.caiYunKey}")
    private String caiYunKey;
    @Value("${pub.izumi.weather.caiYunLocation}")
    private String caiYunLocation;
    @Value("${pub.izumi.QQGroup}")
    private Long QQGroup;

    private static final Logger logger = LoggerFactory.getLogger(WeatherSchedule.class);
    private RestTemplate restTemplate = new RestTemplate();

    public WeatherSchedule() {
        logger.info("-----开始天气监控-----");
    }

    /**
     * 请求彩云天气 "小时级预报接口"，当天有雨时qq预警。
     */
    @Scheduled(cron = "0 0 */4 * * ?")
    public void getWeather() {
        if (robots.values().iterator().hasNext()) {
            Set<Object> result = new LinkedHashSet<>(8);
            CoolQ coolQ = robots.values().iterator().next();
            logger.info("获取一次天气 {}", coolQ.toString());
            String url = caiYunUrl + caiYunKey + caiYunLocation;
            Map<String, Object> param = new HashMap<String, Object>() {
                {
                    put("lang", "zh_CN");
                    put("hourlysteps", 10);
                    put("unit", "metric:v1");
                }
            };
            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, null, String.class, param);
            JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
            jsonObject = jsonObject.getJSONObject("result").getJSONObject("hourly");
            String description = jsonObject.getString("description");
            result.add(description);
            result.add(jsonObject.get("forecast_keypoint"));
            JSONArray jsonArray = jsonObject.getJSONArray("precipitation");
            jsonArray.forEach(o -> {
                // 雷达降水强度（0 ~ 1）判断降水等级：0.03~0.25 小雨(雪)， 0.25~0.35 中雨(雪)， 0.35~0.48大雨(雪)， >0.48 暴雨(雪)；
                if (JSONObject.parseObject(o.toString()).getDouble("value") > 0.1) {
                    result.add(o);
                }
            });
            if (description.contains("雨") || description.contains("雪") || result.size() > 3) {
                result.remove(null);
                MessageGroup messageGroup = new MessageGroup(QQGroup, coolQ.getSelfId(),
                        roleService.getAtqqlistById(weatherRole) + result.toString());
                msgCenter.response(coolQ, messageGroup);
            }
        }
    }

}
