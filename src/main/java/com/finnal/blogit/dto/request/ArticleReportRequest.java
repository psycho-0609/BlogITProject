package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class ArticleReportRequest {
    private Integer reportId;
    private Long articleId;
    private String content;
}
