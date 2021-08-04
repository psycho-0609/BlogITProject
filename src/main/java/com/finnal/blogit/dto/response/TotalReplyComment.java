package com.finnal.blogit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalReplyComment {
    private Long articleId;
    private Long total;
}
