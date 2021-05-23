package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Long> {
}
