package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum ArticleNew {
    ENABLE(1),DISABLE(2);
    private Integer value;

    private ArticleNew(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static ArticleNew fromValue(Integer value) {
        return Stream.of(ArticleNew.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
