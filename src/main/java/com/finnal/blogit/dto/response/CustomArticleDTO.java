package com.finnal.blogit.dto.response;


import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class CustomArticleDTO {
    private Long id;
    private String title;
    private ArticlePublished published;
    private ArticleNew news;
    private ArticleStatus status;
    private Long countView;
    private String imagePath;
    private String shortDescription;
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdDate;
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private LocalDateTime publishedDate;
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private LocalDateTime modifiedDate;
    private TopicResponse topic;
    private CustomUserAccount userAccount;
    public CustomArticleDTO(){}

    public CustomArticleDTO(Long id) {
        this.id = id;
    }

    public Integer getPublished() {
        return published == null ? null : published.getValue();
    }

    public void setPublished(ArticlePublished published) {
        this.published = published;
    }

    public Integer getStatus() {
        return status == null ? null : status.getValue();
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public Integer getNews() {
        return news == null ? null : news.getValue() ;
    }

    public CustomArticleDTO(Long id, String title, ArticlePublished published, ArticleNew news,ArticleStatus status, Long countView,
                            String imagePath, String shortDescription, LocalDateTime createdDate, LocalDateTime publishedDate,
                            LocalDateTime modifiedDate, Integer topicId, String topicName, Long accountId,
                            String email, Long userDetailId, String firstName, String lastName, String thumbnail){

        CustomerUserDetailDTO userDetailDTO = new CustomerUserDetailDTO(userDetailId, firstName,lastName,thumbnail);
        this.id = id;
        this.title = title;
        this.published = published;
        this.news = news;
        this.countView = countView;
        this.imagePath = imagePath;
        this.shortDescription = shortDescription;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.publishedDate = publishedDate;
        this.topic = new TopicResponse(topicId,topicName);
        this.userAccount = new CustomUserAccount(accountId, email, userDetailDTO);
        this.status = status;

    }

    public String getImagePath() {
        if(imagePath == null|| id == null) return null;
        return "/imgBackArticle/" + id + "/" + imagePath;
    }
}
