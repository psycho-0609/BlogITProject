package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.UserAccountEntity;

public interface IUserAccount {

    Iterable<UserAccountEntity> findAll();
    UserAccountEntity createUserAccountEntity(UserAccountEntity entity);

}
