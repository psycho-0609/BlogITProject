package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.FavoriteEntity;

import java.util.Optional;

public interface IFavoriteService {

    Optional<FavoriteEntity> findByAccountId(Long id);
}
