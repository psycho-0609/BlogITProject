package com.ckfinder.demo.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ArticleDTO extends AbstractClass{
    private String content;
    private String title;
    private String shortDescription;
    private String file;
    private String base64;
    private Boolean isPublic;

    public String getBase64() {
        return base64.split(",")[1];
    }


}
