package com.finnal.blogit.controller.api.admin;

import com.finnal.blogit.dto.response.ChatResponse;
import com.finnal.blogit.dto.response.HistoryChat;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.MessageChat;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IChatService;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/chat")
public class ChatAPI {

    @Autowired
    private IUserAccountService uService;

    @Autowired
    private IChatService service;

    @GetMapping("/getUser")
    public ResponseEntity<List<UserInforDto>> getUser(@RequestParam(value = "name", required = false) String name){
        if(name != null && !Strings.isEmpty(name)){
            return new ResponseEntity<>( uService.getAllUserByName(name), HttpStatus.OK);
        }else{
            return new ResponseEntity<>( uService.getAllUser(), HttpStatus.OK);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<HistoryChat> getMessages(@RequestParam(value = "userId") Long id) throws APIException {
        HistoryChat historyChat = new HistoryChat();
        UserInforDto user = uService.findUserById(id).orElseThrow(()-> new ItemNotFoundException("User not found"));
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        historyChat.setUser(user);
        historyChat.setMessages(service.findAllByUserIdAndOtherUserId(userDetail.getId(), id));
        return new ResponseEntity<>(historyChat, HttpStatus.OK);
    }
}
