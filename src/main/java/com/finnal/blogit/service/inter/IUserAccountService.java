package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.dto.request.RegisterRequest;

import java.util.Optional;

public interface IUserAccountService {

    UserAccountEntity registryAccount(RegisterRequest request);
    Optional<UserAccountEntity> findByUsername(String username);
    UserAccountEntity updateResetToken(UserAccountEntity userAccountEntity, String token);
    Optional<UserAccountEntity> findByToken(String token);
    Optional<UserAccountEntity> findById(Long id);
    UserAccountEntity save(UserAccountEntity entity);
    Optional<UserAccountEntity> findByEmail(String email);

}
