package com.finnal.blogit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCustomDTO {
    private Long id;
    private String pathImage;
    private String title;
    private String shortDescription;
    private Date publicDate;
}
