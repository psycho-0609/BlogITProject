package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum NotificationTypeType {
    NEW_POST_SUBMITTED(1),REPORT(2), APPROVED(3), DENINED(4);
    private Integer value;

    private NotificationTypeType(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static NotificationTypeType fromValue(Integer value) {
        return Stream.of(NotificationTypeType.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
