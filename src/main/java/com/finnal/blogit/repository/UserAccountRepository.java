package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.CustomUserAccount;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity,Long> {
//    UserAccountEntity findByEmail(String email);
    Optional<UserAccountEntity> findByToken(String token);
    Optional<UserAccountEntity> findByEmail(String email);
    Optional<UserAccountEntity> findByEmailAndStatus(String email, AccountStatus status);

    @Query("select new com.finnal.blogit.dto.response.CustomUserAccount(u.id, u.email, u.status, u.userDetailEntity.id, " +
            "u.userDetailEntity.firstName, u.userDetailEntity.lastName, u.userDetailEntity.thumbnail) from UserAccountEntity as u where u.role.name = 'USER'")
    List<CustomUserAccount> findAllUser();

    @Query("select new com.finnal.blogit.dto.response.CustomUserAccount(u.id, u.email, u.status, u.userDetailEntity.id, " +
            "u.userDetailEntity.firstName, u.userDetailEntity.lastName, u.userDetailEntity.thumbnail) from UserAccountEntity as u where u.id = :id")
    Optional<CustomUserAccount> findOneById(@Param("id") Long id);

    @Query("select count(ua) from UserAccountEntity ua where ua.status = 1 and ua.role.name = 'USER'")
    Long countALLByStatus();
}
