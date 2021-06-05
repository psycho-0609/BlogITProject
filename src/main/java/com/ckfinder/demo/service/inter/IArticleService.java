package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.dto.request.ArticleRequest;
import com.ckfinder.demo.dto.response.CustomArticleDTO;
import com.ckfinder.demo.entity.ArticleEntity;

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
//    ResponseArticleDTO changeStatus(ArticleRequest dto);
    void plusCountView(ArticleEntity articleEntity);
    ArticleEntity save(ArticleEntity articleEntity);
}
