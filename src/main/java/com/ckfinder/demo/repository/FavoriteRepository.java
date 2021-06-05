package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Long> {

    Optional<FavoriteEntity> findAllByUserAccount_Id(Long id);
}
