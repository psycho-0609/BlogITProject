package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class RateRequest {
    private Long articleId;
    private Integer score;
}
