package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class PaginationArticleDTO {
    private List<CustomArticleDTO> articles;
    private Long totalPage;
}
