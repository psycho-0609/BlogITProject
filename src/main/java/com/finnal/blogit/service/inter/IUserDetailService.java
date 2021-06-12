package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.UserDetailEntity;

import java.util.Optional;

public interface IUserDetailService {

    Optional<UserDetailEntity> findById(Long id);
    UserDetailEntity save(UserDetailEntity entity);
    Optional<UserDetailEntity> findByAccountId(Long id);
}
