package pub.izumi.coolqs.core.bean;

import lombok.Data;

/**
 * @author izumi
 */
@Data
public class MessageGroup extends Message {
    private Integer age;
    private String sex;
    /**
     * 群聊等级
     */
    private String level;
    /**
     * 群角色
     */
    private String role;

    private long groupId;

    public MessageGroup(String subType, String messageType, long userId, String nickname, String msg, String postType, Integer age, String sex, String level, String role, long groupId) {
        super(subType, messageType, userId, nickname, msg, postType);
        this.age = age;
        this.sex = sex;
        this.level = level;
        this.role = role;
        this.groupId = groupId;
    }
}
