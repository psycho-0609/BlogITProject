package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity,Long> {
    UserAccountEntity findByEmail(String email);
}
