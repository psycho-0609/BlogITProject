package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.ArticleReportEntity;

import java.util.Optional;

public interface IArticleReportService {

    ArticleReportEntity save(ArticleReportEntity entity);
    void delete(Long id);
    Optional<ArticleReportEntity> findById(Long id);
}
