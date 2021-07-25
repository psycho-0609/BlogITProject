package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class CommentRequest {
    private Long id;
    private String content;
    private Long articleId;

}
