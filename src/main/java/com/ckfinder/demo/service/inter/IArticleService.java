package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.request.ArticleRequest;
import com.ckfinder.demo.entity.ArticleEntity;

import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<ArticleEntity> findAll();
    ArticleEntity insertArticle(ArticleRequest articleEntity);
    ArticleEntity findOne(Long id);
    ArticleEntity update(ArticleRequest articleRequest);
    Optional<ArticleEntity> findById(Long id);
    void deleteArticle(Long id);
    ArticleEntity changeStatus(ArticleRequest dto);
    void plusCountView(ArticleEntity articleEntity);
}
