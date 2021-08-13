package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.dto.response.HistoryChat;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.service.inter.IChatService;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/chat")
public class ChatUserAPI {

    @Autowired
    private IUserAccountService service;

    @Autowired
    private IChatService chatService;

    @GetMapping("/history")
    public ResponseEntity<HistoryChat> getAdmin(){
        HistoryChat historyChat = new HistoryChat();
        UserInforDto userInfor = service.getInforAdmin().get(0);
        historyChat.setUser(userInfor);
        historyChat.setMessages(chatService.findAllByUserIdAndOtherUserId(UserInfor.getPrincipal().getId(), userInfor.getId()));
        return new ResponseEntity<>(historyChat, HttpStatus.OK);
    }

}
