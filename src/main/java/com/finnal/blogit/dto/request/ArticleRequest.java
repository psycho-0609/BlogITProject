package com.finnal.blogit.dto.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
public class ArticleRequest extends AbstractClass{
    private MultipartFile image;
    private MultipartFile video;
    private String content;
    private String title;
    private String shortDescription;
    private Integer topicId;
    private Integer status;
}
