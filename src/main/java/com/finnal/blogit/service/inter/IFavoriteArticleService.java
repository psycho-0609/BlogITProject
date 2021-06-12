package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.FavoriteArticleEntity;

import java.util.Optional;

public interface IFavoriteArticleService {

    FavoriteArticleEntity create(Long favId, Long articleId);

    Optional<FavoriteArticleEntity> findByFavIdAndArticleId(Long favId, Long articleId);
    Optional<FavoriteArticleEntity> findById(Long id);
    Long countByArticleId(Long id);
    void deleteById(Long id);
}
