package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.ArticleReportEntity;

import java.util.Optional;

public interface IArticleReportService {

    ArticleReportEntity save(ArticleReportEntity entity);
    void delete(Long id);
    Optional<ArticleReportEntity> findById(Long id);
}
