package com.finnal.blogit.repository;

import com.finnal.blogit.entity.ReadLaterArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadLaterArticleRepository extends JpaRepository<ReadLaterArticleEntity,Long> {

    Optional<ReadLaterArticleEntity> findByReadLaterEntity_IdAndArticleEntity_Id(Long readLaterId, Long articleId);
    Long countAllByReadLaterEntityId(Long id);
}
