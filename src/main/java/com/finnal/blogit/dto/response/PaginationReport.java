package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginationReport {

    private Long totalPage;
    private List<GetListReport> reports;
}
