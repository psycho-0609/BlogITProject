package com.ckfinder.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CustomArticleDTO {
    private Long id;
    private String title;
    private Boolean isPublic;
    private Boolean isNew;
    private CustomUserAccount userAccount;
    private Long countView;
    private String imagePath;
    private String shortDescription;
    public CustomArticleDTO(){}

    public CustomArticleDTO(Long id) {
        this.id = id;
    }


}
