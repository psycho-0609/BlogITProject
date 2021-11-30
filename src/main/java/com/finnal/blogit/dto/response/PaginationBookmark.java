package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginationBookmark {
    private Long totalPage;
    private List<GetListBookMark> bookmark;
}
