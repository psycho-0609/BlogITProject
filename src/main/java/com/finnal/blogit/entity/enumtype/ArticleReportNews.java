package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum ArticleReportNews {
    ENABLE(1), DISABLE(2);

    private Integer value;
    private ArticleReportNews(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static ArticleReportNews fromValue(Integer value) {
        return Stream.of(ArticleReportNews.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
