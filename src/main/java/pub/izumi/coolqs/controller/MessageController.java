package pub.izumi.coolqs.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.izumi.coolqs.bean.Message;
import pub.izumi.coolqs.mapper.MessageMapper;

/**
 * @author izumi
 */
@RestController
@RequestMapping(value = "/msg")
public class MessageController {

    @Autowired
    MessageMapper messageMapper;

    /**
     * 保存个人聊天记录
     */
    @PostMapping("/get_all")
    @ApiOperation(value = "保存消息记录", tags = {"私聊消息"})
    public void saveMsg(Message message){
        messageMapper.insert(message);
    }
}
