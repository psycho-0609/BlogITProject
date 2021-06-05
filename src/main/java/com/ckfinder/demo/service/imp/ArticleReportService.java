package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.ArticleReportEntity;
import com.ckfinder.demo.repository.ArticleReportRepository;
import com.ckfinder.demo.service.inter.IArticleReportService;
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
