package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum AccountStatus {
    ENABLE(1),DISABLE(2);
    private Integer value;

    private AccountStatus(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static AccountStatus fromValue(Integer value) {
        return Stream.of(AccountStatus.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
