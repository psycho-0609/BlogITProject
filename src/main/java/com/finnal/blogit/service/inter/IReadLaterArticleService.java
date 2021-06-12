package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.ReadLaterArticleEntity;

import java.util.Optional;

public interface IReadLaterArticleService {
    ReadLaterArticleEntity create(Long readLaterId, Long articleId);
    Optional<ReadLaterArticleEntity> findByReadLaterIdAndArticleId(Long readLaterId, Long articleId);
    Optional<ReadLaterArticleEntity> findById(Long id);
    void delete(Long id);
    void deleteAll();
    Long countItemByReadLaterId(Long id);
}
