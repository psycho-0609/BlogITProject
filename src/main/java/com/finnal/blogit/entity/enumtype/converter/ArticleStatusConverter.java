package com.finnal.blogit.entity.enumtype.converter;

import com.finnal.blogit.entity.enumtype.ArticleStatus;

import javax.persistence.AttributeConverter;

public class ArticleStatusConverter implements AttributeConverter<ArticleStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(ArticleStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ArticleStatus convertToEntityAttribute(Integer dbData) {
        return ArticleStatus.fromValue(dbData);
    }
}
