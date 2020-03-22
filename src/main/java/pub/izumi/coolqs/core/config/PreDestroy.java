package pub.izumi.coolqs.core.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.MsgCenter;

@Component
public class PreDestroy {
    @Autowired
    MsgCenter msgCenter;

    @javax.annotation.PreDestroy
    public void destroy() {
        msgCenter.saveMsgWhenExit();
    }
}
