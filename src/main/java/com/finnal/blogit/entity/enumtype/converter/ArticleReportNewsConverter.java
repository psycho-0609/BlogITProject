package com.finnal.blogit.entity.enumtype.converter;

import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;

import javax.persistence.AttributeConverter;

public class ArticleReportNewsConverter implements AttributeConverter<ArticleReportNews, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ArticleReportNews attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ArticleReportNews convertToEntityAttribute(Integer dbData) {
        return ArticleReportNews.fromValue(dbData);
    }

}
