package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class HistoryArticleDTO {
    private Long id;
    private Date createdDate;
    private CustomArticleDTO article;

    public HistoryArticleDTO(){}

    public HistoryArticleDTO(Long id, Date createdDate, Long articleId, String title, ArticlePublished published, ArticleNew news, ArticleStatus status, Long countView,
                             String imagePath, String shortDescription, LocalDateTime articleCreatedDate, LocalDateTime publishedDate,
                             LocalDateTime modifiedDate, Integer topicId, String topicName, Long accountId,
                             String email, Long userDetailId, String firstName, String lastName, String thumbnail) {
        this.id = id;
        this.createdDate = createdDate;
        this.article = new CustomArticleDTO(articleId, title, published,news, status, countView,imagePath, shortDescription, articleCreatedDate,
                        publishedDate,modifiedDate,topicId,topicName,accountId,email,userDetailId,firstName,lastName,thumbnail);
    }
}
