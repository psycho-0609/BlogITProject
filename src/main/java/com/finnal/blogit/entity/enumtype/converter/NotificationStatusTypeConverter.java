package com.finnal.blogit.entity.enumtype.converter;

import com.finnal.blogit.entity.enumtype.NotificationStatusType;

import javax.persistence.AttributeConverter;

public class NotificationStatusTypeConverter implements AttributeConverter<NotificationStatusType,Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotificationStatusType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public NotificationStatusType convertToEntityAttribute(Integer dbData) {
        return NotificationStatusType.fromValue(dbData);
    }
}
