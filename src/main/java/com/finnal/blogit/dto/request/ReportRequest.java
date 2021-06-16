package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class ReportRequest {
    private Integer typeReportId;
    private Long articleId;
    private String comment;
}
