package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListReportArticleDTO {

    private Long id;
    private String comment;
    private ArticleReportNews news;
    private LocalDateTime createdDate;
    private ReasonReportDTO reasonReport;

    public ListReportArticleDTO(Long id, String comment, ArticleReportNews news, LocalDateTime createdDate, Integer reasonId, String name){
        this.id = id;
        this.comment = comment;
        this.news = news;
        this.createdDate = createdDate;
        this.reasonReport = new ReasonReportDTO(reasonId, name);
    }
}
