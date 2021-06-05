package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity,Long> {
}
