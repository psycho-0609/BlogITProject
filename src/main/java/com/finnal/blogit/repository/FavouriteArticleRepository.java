package com.finnal.blogit.repository;

import com.finnal.blogit.entity.FavoriteArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavouriteArticleRepository extends JpaRepository<FavoriteArticleEntity,Long> {

    Optional<FavoriteArticleEntity> findByFavoriteEntity_IdAndArticleEntity_Id(Long FavId, Long ArticleId);
    Long countAllByArticleEntityId(Long id);
}
