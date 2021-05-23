package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.ReadLaterArticleEntity;

import java.util.Optional;

public interface IReadLaterArticleService {
    ReadLaterArticleEntity create(Long readLaterId, Long articleId);
    Optional<ReadLaterArticleEntity> findByReadLaterIdAndArticleId(Long readLaterId, Long articleId);
}
