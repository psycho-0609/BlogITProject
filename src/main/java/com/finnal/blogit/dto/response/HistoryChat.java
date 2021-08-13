package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class HistoryChat {
    private UserInforDto user;
    private List<ChatResponse> messages;
}
