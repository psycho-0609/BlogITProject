package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCustomDTO {
    private Long id;
    private String title;
    private ArticlePublished published;
    private Integer prioritize;

    public Integer getPublished() {
        return published == null ? null : published.getValue();
    }


}
