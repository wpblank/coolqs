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

/**
 * @author izumi
 */
@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    ChatMapper chatMapper;

    @GetMapping("")
    public ResponseEntity<Object> getMessageList(@RequestParam("qq") String qq) {
        return new ResponseEntity<>(chatMapper.selectList(null), HttpStatus.OK);
    }

}
