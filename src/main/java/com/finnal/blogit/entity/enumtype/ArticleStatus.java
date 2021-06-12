package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum ArticleStatus {
    PUBLIC(1),PRIVATE(2);

    private Integer value;

    private ArticleStatus(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static ArticleStatus fromValue(Integer value) {
        return Stream.of(ArticleStatus.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
