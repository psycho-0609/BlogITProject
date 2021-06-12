package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.UserAccountEntity;

import java.util.Optional;

public interface IUserAccount {

    Iterable<UserAccountEntity> findAll();
    UserAccountEntity createUserAccountEntity(UserAccountEntity entity);
    Optional<UserAccountEntity> findById(Long id);

}
