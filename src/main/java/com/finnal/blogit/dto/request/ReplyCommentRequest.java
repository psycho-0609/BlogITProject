package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class ReplyCommentRequest {
    private Long id;
    private Long commentId;
    private String content;
}
