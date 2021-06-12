package com.finnal.blogit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "readlater_article")
@Getter
@Setter
public class ReadLaterArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "later_id")
    private ReadLaterEntity readLaterEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity articleEntity;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

}
