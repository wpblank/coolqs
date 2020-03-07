package pub.izumi.coolqs.bean;

import lombok.Data;

/**
 * @author izumi
 */
@Data
public class MessageGroup extends Message {
    private long fromGroup;
    private String fromAnonymous;
}
