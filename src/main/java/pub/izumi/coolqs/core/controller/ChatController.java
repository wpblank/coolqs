package pub.izumi.coolqs.core.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pub.izumi.coolqs.core.bean.Chat;
import pub.izumi.coolqs.core.bean.Message;
import pub.izumi.coolqs.core.mapper.ChatMapper;
import pub.izumi.coolqs.core.mapper.MessageMapper;
import pub.izumi.coolqs.core.service.ChatService;
import pub.izumi.coolqs.core.service.UserService;

/**
 * @author izumi
 */
@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    ChatMapper chatMapper;
    @Autowired
    MessageMapper messageMapper;

    @GetMapping("")
    public ResponseEntity<Object> getMessageList(@RequestParam("qq") String qq) {
        return new ResponseEntity<>(ChatService.chatLinkedList, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<Object> getUserList(@RequestParam("qq") String qq) {
        return new ResponseEntity<>(UserService.userList, HttpStatus.OK);
    }

    @GetMapping("test")
    public ResponseEntity<Object> hasTable(@RequestParam("message") String message) {
        return new ResponseEntity<>(messageMapper.hasTable(message), HttpStatus.OK);
    }

}
