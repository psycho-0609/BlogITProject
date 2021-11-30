package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginationComment {
    private Long totalPage;
    private List<ListArticleComment> list;
}
