package com.finnal.blogit.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class MessageRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private String content;
    private Date createdDate;
}
