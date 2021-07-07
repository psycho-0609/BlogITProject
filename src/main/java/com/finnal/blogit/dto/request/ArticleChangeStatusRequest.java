package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class ArticleChangeStatusRequest {

    private Long id;
    private Integer published;
    private Integer prioritize;
}
