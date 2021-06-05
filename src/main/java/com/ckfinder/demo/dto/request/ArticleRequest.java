package com.ckfinder.demo.dto.request;


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
    private Boolean isPublic;
    private Integer topicId;

    public String getBase64() {
        return base64.isEmpty() || base64 == null ? null : base64.split(",")[1];
    }


}
