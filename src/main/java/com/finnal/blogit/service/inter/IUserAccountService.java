package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.CustomUserAccount;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.dto.request.RegisterRequest;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserAccountService {

    UserAccountEntity registryAccount(RegisterRequest request);
    Optional<UserAccountEntity> findByUsername(String username);
    UserAccountEntity updateResetToken(UserAccountEntity userAccountEntity, String token);
    Optional<UserAccountEntity> findByToken(String token);
    Optional<UserAccountEntity> findById(Long id);
    UserAccountEntity save(UserAccountEntity entity);
    Optional<UserAccountEntity> findByEmail(String email);
    Optional<UserAccountEntity> findByEmailAndStatus(String email, AccountStatus status);
    List<CustomUserAccount> getAllAccountUser();
    Optional<CustomUserAccount> findOneById(Long id);
    Long accountAllAccountByStatus();
    boolean existsById(Long id);
    List<UserAccountEntity> findAccountAdmin();
    List<UserInforDto> getAllUser();
    List<UserInforDto> getAllUserByName(String name);
    Optional<UserInforDto> findUserById(Long id);
    List<UserInforDto> getInforAdmin();
    Page<Long> getListIdForPagi(Pageable pageable, String email);
    List<CustomUserAccount> getAllByListId(List<Long> ids);
    Long countAllAccount(String email);



}
