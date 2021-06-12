package com.finnal.blogit.repository;

import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity,Long> {
//    UserAccountEntity findByEmail(String email);
    Optional<UserAccountEntity> findByToken(String token);
    Optional<UserAccountEntity> findByEmail(String email);
    Optional<UserAccountEntity> findByEmailAndStatus(String email, AccountStatus status);
}
