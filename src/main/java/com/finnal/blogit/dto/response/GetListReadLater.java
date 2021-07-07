package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetListReadLater {
    private Long id;
    private CustomArticleDTO article;

    public GetListReadLater(Long id, Long articleId, String title, ArticlePublished published, ArticleNew news, ArticleStatus status, Long countView,
                         String imagePath, String shortDescription, LocalDateTime articleCreatedDate, LocalDateTime publishedDate,
                         LocalDateTime modifiedDate, Integer prioritize, Integer topicId, String topicName, Long accountId,
                         String email, Long userDetailId, String firstName, String lastName, String thumbnail){

        this.id = id;
        this.article = new CustomArticleDTO(articleId, title, published,news, status, countView,imagePath, shortDescription, articleCreatedDate,
                publishedDate,modifiedDate, prioritize, topicId,topicName,accountId,email,userDetailId,firstName,lastName,thumbnail);
    }
}
