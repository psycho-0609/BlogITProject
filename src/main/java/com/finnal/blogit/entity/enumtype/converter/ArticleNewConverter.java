package com.finnal.blogit.entity.enumtype.converter;

import com.finnal.blogit.entity.enumtype.ArticleNew;


import javax.persistence.AttributeConverter;

public class ArticleNewConverter implements AttributeConverter<ArticleNew,Integer> {
    @Override
    public Integer convertToDatabaseColumn(ArticleNew attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ArticleNew convertToEntityAttribute(Integer dbData) {
        return ArticleNew.fromValue(dbData);
    }
}
