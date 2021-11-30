package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum ScoreRateType {
    BAD(1),NOT_BAD(2), MEDIUM(3), GOOD(4), EXCELLENT(5);
    private Integer value;

    private ScoreRateType(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static ScoreRateType fromValue(Integer value) {
        return Stream.of(ScoreRateType.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
