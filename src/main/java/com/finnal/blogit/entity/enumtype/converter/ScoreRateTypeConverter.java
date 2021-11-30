package com.finnal.blogit.entity.enumtype.converter;
import com.finnal.blogit.entity.enumtype.ScoreRateType;

import javax.persistence.AttributeConverter;

public class ScoreRateTypeConverter implements AttributeConverter<ScoreRateType,Integer> {

    @Override
    public Integer convertToDatabaseColumn(ScoreRateType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ScoreRateType convertToEntityAttribute(Integer dbData) {
        return ScoreRateType.fromValue(dbData);
    }
}
