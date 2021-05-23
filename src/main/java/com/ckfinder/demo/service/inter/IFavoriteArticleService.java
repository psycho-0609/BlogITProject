package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.FavoriteArticleEntity;

import java.util.Optional;

public interface IFavoriteArticleService {

    FavoriteArticleEntity create(Long favId, Long articleId);

    Optional<FavoriteArticleEntity> findByFavIdAndArticleId(Long favId, Long articleId);
}
