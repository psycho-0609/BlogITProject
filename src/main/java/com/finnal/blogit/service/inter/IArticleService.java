package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.request.ArticleRequest;
import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;

import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<ArticleEntity> findAll();
    List<CustomArticleDTO> findAllApi();
    ArticleEntity insertArticle(ArticleRequest articleEntity);
    ArticleEntity findOne(Long id);
    ArticleEntity update(ArticleRequest articleRequest);
    Optional<ArticleEntity> findById(Long id);
    void deleteArticle(Long id);
    void plusCountView(ArticleEntity articleEntity);
    ArticleEntity save(ArticleEntity articleEntity);
    List<CustomArticleDTO> findAllByPublishedStatusAndAccount(ArticlePublished published, Long id, ArticleStatus status);
    List<CustomArticleDTO> findAllByAccountId(Long id);
    List<CustomArticleDTO> findAllForSearch(ArticlePublished published, Long id, ArticleStatus status, String title);
    List<CustomArticleDTO> findByPublishedAndStatus(ArticlePublished published, ArticleStatus status);
    List<CustomArticleDTO> findAllByTopicId(Integer id);
    Long totalCountView(List<Long> ids);

}
