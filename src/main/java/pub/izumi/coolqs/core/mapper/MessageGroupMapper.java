package pub.izumi.coolqs.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
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
    int insertBatch(List<MessageGroup> list);
}
