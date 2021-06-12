package com.finnal.blogit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ArticleReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private ReportEntity reportEntity;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity articleEntity;

    private String content;
}
