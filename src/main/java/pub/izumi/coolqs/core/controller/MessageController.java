package pub.izumi.coolqs.core.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.bean.MessageGroup;
import pub.izumi.coolqs.core.mapper.MessageGroupMapper;
import pub.izumi.coolqs.core.mapper.MessageMapper;

/**
 * @author izumi
 */
@RestController
@RequestMapping(value = "/msg")
public class MessageController {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MessageGroupMapper messageGroupMapper;

    /**
     * 保存个人聊天记录
     */
    @PostMapping("/private/save")
    @ApiOperation(value = "保存消息记录", tags = {"私聊消息"})
    public ResponseEntity<Object> saveMsg(@RequestBody Message message) {
        if (messageMapper.insert(message) > 0) {
            return new ResponseEntity<>("保存成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 保存群聊天记录
     */
    @PostMapping("/group/save")
    @ApiOperation(value = "保存消息记录", tags = {"群聊消息"})
    public ResponseEntity<Object> saveMsgGroup(@RequestBody MessageGroup messageGroup) {
        if (messageGroupMapper.insert(messageGroup) > 0) {
            return new ResponseEntity<>("保存成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
