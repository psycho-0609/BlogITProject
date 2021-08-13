package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.ChatResponse;
import com.finnal.blogit.entity.MessageChat;
import com.finnal.blogit.repository.MessageChatRepository;
import com.finnal.blogit.service.inter.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageChatService implements IChatService {

    @Autowired
    private MessageChatRepository repository;

    @Override
    public MessageChat save(MessageChat messageChat) {
        return repository.save(messageChat);
    }

    @Override
    public List<ChatResponse> findAllByUserIdAndOtherUserId(Long id, Long otherId) {
        return repository.findAllByUserIdAndOtherUserId(id, otherId);
    }
}
