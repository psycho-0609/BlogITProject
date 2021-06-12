package com.finnal.blogit.entity.enumtype.converter;

import com.finnal.blogit.entity.enumtype.AccountStatus;

import javax.persistence.AttributeConverter;

public class AccountStatusConverter implements AttributeConverter<AccountStatus,Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public AccountStatus convertToEntityAttribute(Integer dbData) {
        return AccountStatus.fromValue(dbData);
    }
}
