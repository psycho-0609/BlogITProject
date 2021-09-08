package com.finnal.blogit.entity.enumtype;

import java.util.stream.Stream;

public enum NotificationStatusType {
    NEW(1),NOT(2);
    private Integer value;

    private NotificationStatusType(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public static NotificationStatusType fromValue(Integer value) {
        return Stream.of(NotificationStatusType.values()).filter(c -> c.getValue().equals(value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
