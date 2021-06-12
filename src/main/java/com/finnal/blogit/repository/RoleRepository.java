package com.finnal.blogit.repository;

import com.finnal.blogit.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    RoleEntity findByName(String name);
}
