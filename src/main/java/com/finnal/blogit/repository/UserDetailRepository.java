package com.finnal.blogit.repository;

import com.finnal.blogit.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity,Long> {

    Optional<UserDetailEntity> findByUserAccountId(Long id);
}
