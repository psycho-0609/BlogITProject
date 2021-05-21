package com.ckfinder.demo.service;

import com.ckfinder.demo.dto.ArticleDTO;
import com.ckfinder.demo.entity.ArticleEntity;

import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<ArticleEntity> findAll();
    ArticleEntity insertArticle(ArticleDTO articleEntity);
    ArticleEntity findOne(Long id);
    ArticleEntity update(ArticleDTO articleDTO);
    Optional<ArticleEntity> findById(Long id);
    void deleteArticle(Long id);
    ArticleEntity changeStatus(ArticleDTO dto);
}
