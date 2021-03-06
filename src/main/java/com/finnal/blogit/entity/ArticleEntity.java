package com.finnal.blogit.entity;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.entity.enumtype.converter.ArticleNewConverter;
import com.finnal.blogit.entity.enumtype.converter.ArticlePublishedConverter;
import com.finnal.blogit.entity.enumtype.converter.ArticleStatusConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(length = 10485760)
    private String content;

    @Column
    private String videoFile;

    @Column(length = 3000)
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
    private List<BookMarkEntity> readLaterArticle;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<RateEntity> rates;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccountEntity userAccount;

    @Column
    private Long countView;

    @Column
    private Integer prioritize;

    private Double avgRate;

    @OneToMany(mappedBy = "article")
    private List<RateEntity> rate;

    @Transient
    public String getImagePath(){
        if(image == null|| id == null) return null;
//        return Constant.FIREBASE_URL + Constant.BUCKET_NAME + "/fileArticle/" + id + "/" + image;
         return "/imgBackArticle/" + id + "/" + image;
    }

    public ArticleEntity(Long id) {
        this.id = id;
    }

    @Transient
    public String getVideoPath(){
        if(videoFile == null || Strings.isEmpty(videoFile) || id == null) return null;
        return Constant.FIREBASE_URL + Constant.BUCKET_NAME + "/fileArticle/" + id + "/" + videoFile;
    }
}
