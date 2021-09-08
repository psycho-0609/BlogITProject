package com.finnal.blogit.entity;

import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import com.finnal.blogit.entity.enumtype.converter.ArticleReportNewsConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime createdDate;

    @Convert(converter = ArticleReportNewsConverter.class)
    private ArticleReportNews news;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;
}
