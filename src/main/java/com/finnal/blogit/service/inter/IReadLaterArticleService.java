package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.GetListReadLater;
import com.finnal.blogit.entity.ReadLaterArticleEntity;

import java.util.List;
import java.util.Optional;

public interface IReadLaterArticleService {
    ReadLaterArticleEntity create(Long readLaterId, Long articleId);
    Optional<ReadLaterArticleEntity> findByAccountIdAndArticleId(Long accountId, Long articleId);
    Optional<ReadLaterArticleEntity> findById(Long id);
    void delete(Long id);
    void deleteAll();
    Long countItemByReadLaterId(Long id);
    List<GetListReadLater> findAllByAccountId(Long id);
    List<GetListReadLater> findForSearch(Long id, String title);
    Long countAllByArticleId(Long id);
}
