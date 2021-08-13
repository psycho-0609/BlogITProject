package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.ChatResponse;
import com.finnal.blogit.entity.MessageChat;

import java.util.List;

public interface IChatService {

    MessageChat save(MessageChat messageChat);
    List<ChatResponse> findAllByUserIdAndOtherUserId(Long id, Long otherId);
}
