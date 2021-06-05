package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.FavoriteEntity;

import java.util.Optional;

public interface IFavoriteService {

    Optional<FavoriteEntity> findByAccountId(Long id);
}
