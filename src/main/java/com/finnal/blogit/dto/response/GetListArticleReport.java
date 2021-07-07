package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GetListArticleReport {
    private ArticleCustomDTO article;
    private List<ListReportArticleDTO> reports;
}
