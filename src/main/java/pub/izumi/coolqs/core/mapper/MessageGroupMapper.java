package pub.izumi.coolqs.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.bean.MessageGroup;

import java.util.List;

/**
 * @author izumi
 */
@Mapper
@Component
public interface MessageGroupMapper extends BaseMapper<MessageGroup> {

    /**
     * 批量插入群聊数据
     */
    int insertBatch(List<MessageGroup> list, @Param("tableName") String tableName);

    /**
     * 查看表是否存在
     *
     * @param tableName 表名
     * @return
     */
    @Select("select count(1) FROM information_schema.tables WHERE table_schema='coolq' and table_name=#{tableName}")
    int hasTable(@Param("tableName") String tableName);

    /**
     * 新建个人聊天记录表
     */
    @Update("create table ${tableName} like message_group")
    int createTable(@Param("tableName") String tableName);
}
