package pub.izumi.coolqs.core.config;

import net.lz1998.cq.CQGlobal;
import net.lz1998.cq.EnableCQ;
import pub.izumi.coolqs.core.MsgCenter;

/**
 * @author izumi
 * 加载插件列表
 */
@EnableCQ
public class CqConfig {
    public static void init() {
        CQGlobal.pluginList.add(MsgCenter.class);
    }
}
