package pub.izumi.coolqs.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.Message;

import java.util.List;

/**
 * @author izumi
 */
@Mapper
@Component
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT * FROM `message` WHERE user_id = #{qq} ORDER BY id DESC")
    List<Message> getMessageList(String qq);

    /**
     * 向指定表插入聊天数据
     */
    @Insert("insert into ${tableName}(nickname,user_id,msg,response,sub_type,message_type,post_type,create_time) " +
            "values(#{message.nickname},#{message.userId},#{message.msg},#{message.response},#{message.subType}," +
            "#{message.messageType},#{message.postType},#{message.createTime})")
    int insertToTable(Message message ,String tableName);

    /**
     * 查看表是否存在
     * @param tableName 表名
     * @return
     */
    @Select("select count(1) FROM information_schema.tables WHERE table_schema='coolq' and table_name=#{tableName}")
    int hasTable(@Param("tableName") String tableName);

    /**
     * 新建个人聊天记录表
     */
    @Update("create table ${tableName} like message")
    int createTable(@Param("tableName") String tableName);
}
