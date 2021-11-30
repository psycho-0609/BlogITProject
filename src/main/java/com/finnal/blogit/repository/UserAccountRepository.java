package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.CustomUserAccount;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select ua from UserAccountEntity ua where ua.role.name = 'ADMIN'")
    List<UserAccountEntity> findAdminAccount();

    @Query("select distinct new com.finnal.blogit.dto.response.UserInforDto(ua.id, ua.userDetailEntity.firstName, ua.userDetailEntity.lastName, ua.userDetailEntity.thumbnail, ua.userDetailEntity.id) " +
            "from UserAccountEntity ua where ua.role.name ='USER'")
    List<UserInforDto> getAllInforUser();

    @Query("select distinct new com.finnal.blogit.dto.response.UserInforDto(ua.id, ua.userDetailEntity.firstName, ua.userDetailEntity.lastName, ua.userDetailEntity.thumbnail, ua.userDetailEntity.id) " +
            "from UserAccountEntity ua where ua.role.name ='USER' and (ua.userDetailEntity.lastName like %:name% or ua.userDetailEntity.firstName like %:name%)")
    List<UserInforDto> getAllInforUserByName(@Param("name") String name);

    @Query("select new  com.finnal.blogit.dto.response.UserInforDto(ua.id, ua.userDetailEntity.firstName, ua.userDetailEntity.lastName, ua.userDetailEntity.thumbnail, ua.userDetailEntity.id) from UserAccountEntity ua where ua.id =:id")
    Optional<UserInforDto> findOneUserById(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.UserInforDto(ua.id, ua.userDetailEntity.firstName, ua.userDetailEntity.lastName, ua.userDetailEntity.thumbnail, ua.userDetailEntity.id) from UserAccountEntity ua where ua.status = 1")
    List<UserInforDto> getInforAdmin();

    @Query("select u.id from UserAccountEntity u where u.role.name='USER' and u.email like %:email%")
    Page<Long> getListIdForPagi(Pageable pageable,@Param("email") String email);

    @Query("select new com.finnal.blogit.dto.response.CustomUserAccount(u.id, u.email, u.status, u.userDetailEntity.id, " +
            "u.userDetailEntity.firstName, u.userDetailEntity.lastName, u.userDetailEntity.thumbnail) from UserAccountEntity as u where u.id in (:ids)")
    List<CustomUserAccount> getAllByListId(List<Long> ids);

    @Query("select count (ua) from UserAccountEntity ua where ua.role.name = 'USER' and ua.email like %:email%")
    Long countAllAccount(@Param("email") String email);
}
