package com.finnal.blogit.dto.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ArticleRequest extends AbstractClass{
    private String content;
    private String title;
    private String shortDescription;
    private String file;
    private String base64;
    private Integer published;
    private Integer topicId;
    private Integer status;


    public String getBase64() {
        return base64 == null || base64.isEmpty() ? null : base64.split(",")[1];
    }


}
