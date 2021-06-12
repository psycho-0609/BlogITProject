package com.finnal.blogit.entity.enumtype.converter;

import com.finnal.blogit.entity.enumtype.ArticlePublished;

import javax.persistence.AttributeConverter;

public class ArticlePublishedConverter implements AttributeConverter<ArticlePublished,Integer> {
    @Override
    public Integer convertToDatabaseColumn(ArticlePublished attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ArticlePublished convertToEntityAttribute(Integer dbData) {
        return ArticlePublished.fromValue(dbData);
    }
}
