package com.finnal.blogit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import lombok.Data;

@Data
public class GetListReport {
    private Long count;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long articleId;
    private ArticleReportNews news;
    private CustomArticleDTO article;

    public GetListReport(Long count, Long articleId) {
        this.count = count;
        this.articleId = articleId;
    }

    public Integer getNews() {
        return news == null ? ArticleReportNews.DISABLE.getValue() : news.getValue();
    }


}
