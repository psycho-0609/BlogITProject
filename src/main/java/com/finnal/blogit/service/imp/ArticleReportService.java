package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.repository.ArticleReportRepository;
import com.finnal.blogit.service.inter.IArticleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleReportService implements IArticleReportService {

    @Autowired
    private ArticleReportRepository repository;

    @Override
    public ArticleReportEntity save(ArticleReportEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ArticleReportEntity> findById(Long id) {
        return repository.findById(id);
    }
}
