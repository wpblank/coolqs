package pub.izumi.coolqs.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.mapper.MessageMapper;

/**
 * @author izumi
 */
@RestController
@RequestMapping("/msg")
@CrossOrigin
public class MessageController {
    @Autowired
    MessageMapper messageMapper;

    @GetMapping("")
    public ResponseEntity<Object> getMessageList(@RequestParam("qq") String qq) {
        PageHelper.startPage(1, 20);
        PageInfo<Message> messageList = new PageInfo<>(messageMapper.getMessageList(qq));
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
}
