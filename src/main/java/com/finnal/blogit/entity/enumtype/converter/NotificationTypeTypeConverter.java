package com.finnal.blogit.entity.enumtype.converter;

import com.finnal.blogit.entity.enumtype.NotificationTypeType;

import javax.persistence.AttributeConverter;

public class NotificationTypeTypeConverter implements AttributeConverter<NotificationTypeType,Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotificationTypeType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public NotificationTypeType convertToEntityAttribute(Integer dbData) {
        return NotificationTypeType.fromValue(dbData);
    }
}
