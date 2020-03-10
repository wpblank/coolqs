package pub.izumi.coolqs.elehb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pub.izumi.coolqs.core.bean.Message;

import java.util.HashMap;
import java.util.Map;


/**
 * @author izumi
 * 用于领取饿了么星选红包
 */
@Service
public class ElehbService {

    @Value("${pub.izumi.elehbUrl}")
    private String elehbUrl;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 领取饿了么星选红包
     *
     * @param msg
     * @return
     */
    public Message getEleHb(Message msg) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> param = new HashMap<>(4);
        param.put("url", msg.getMsg());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(param, requestHeaders);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(elehbUrl, HttpMethod.POST, requestEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        msg.setResponse(jsonObject.getString("message"));
        return msg;
    }

}
