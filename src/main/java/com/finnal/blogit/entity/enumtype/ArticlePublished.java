package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum ArticlePublished {
    ENABLE(1), DISABLE(2);
    private Integer value;
    private ArticlePublished(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static ArticlePublished fromValue(Integer value) {
        return Stream.of(ArticlePublished.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
