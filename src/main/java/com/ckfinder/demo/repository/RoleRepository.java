package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    RoleEntity findByName(String name);
}
