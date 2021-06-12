package com.finnal.blogit.entity;

import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.entity.enumtype.converter.ArticleNewConverter;
import com.finnal.blogit.entity.enumtype.converter.ArticlePublishedConverter;
import com.finnal.blogit.entity.enumtype.converter.ArticleStatusConverter;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "article")
@Data
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column
    private String content;

    @Column
    private String videoFile;

    @Column
    private String shortDescription;

    @Column
    private String image;

    @Column(name = "published")
    @Convert(converter = ArticlePublishedConverter.class)
    private ArticlePublished published;

    @Column(name = "news")
    @Convert(converter = ArticleNewConverter.class)
    private ArticleNew news;

    @Column
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private LocalDateTime publishedDate;

    @Column
    @Convert(converter = ArticleStatusConverter.class)
    private ArticleStatus status;

    @Column
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Column
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "articleEntity", cascade = CascadeType.ALL)
    private List<ArticleReportEntity> articleReportEntities;

    @OneToMany(mappedBy = "articleEntity", cascade = CascadeType.ALL)
    private List<FavoriteArticleEntity> favoriteArticle;

    @OneToMany(mappedBy = "articleEntity", cascade = CascadeType.ALL)
    private List<HistoryArticleEntity> historyArticle;

    @OneToMany(mappedBy = "articleEntity", cascade = CascadeType.ALL)
    private List<ReadLaterArticleEntity> readLaterArticle;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccountEntity userAccount;

    @Column
    private Long countView;

    @Transient
    public String getImagePath(){
        if(image == null|| id==null) return null;
        return "/imgBackArticle/" + id + "/" + image;
    }

}
