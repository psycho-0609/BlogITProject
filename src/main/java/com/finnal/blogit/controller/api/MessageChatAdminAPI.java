package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.request.MessageRequest;
import com.finnal.blogit.dto.response.ChatResponse;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.entity.MessageChat;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IChatService;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
public class MessageChatAdminAPI {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private IChatService service;

    @Autowired
    private IUserAccountService accountService;

    @MessageMapping("/chat/{id}")
    public void sendMessage(@DestinationVariable Long id, MessageRequest request) throws APIException {
        validateUser(id);
        System.out.println(request.getCreatedDate());
        MessageChat messageChat = new MessageChat();
        setParam(request, messageChat,id);
        ChatResponse response = new ChatResponse();
        transferData(response,service.save(messageChat));
        simpMessagingTemplate.convertAndSend("/topic/messages/toUser/"+ id, response);
    }
    private void setParam(MessageRequest request, MessageChat messageChat, Long toId){
        messageChat.setContent(request.getContent());
        messageChat.setFromAccount(new UserAccountEntity(request.getFromAccountId()));
        messageChat.setToAccount(new UserAccountEntity(toId));
        messageChat.setCreatedDate(request.getCreatedDate());

    }

    private void transferData(ChatResponse response, MessageChat messageChat){
        response.setContent(messageChat.getContent());
        response.setFromId(messageChat.getFromAccount().getId());
        response.setToId(messageChat.getToAccount().getId());
        response.setId(messageChat.getId());
        response.setCreatedDate(messageChat.getCreatedDate());
    }

    private void validateUser(Long id) throws APIException{
        if(!accountService.existsById(id)){
            throw new ItemNotFoundException("User not found");
        }
    }
}
