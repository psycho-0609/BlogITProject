package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.ReadLaterArticleEntity;
import com.ckfinder.demo.repository.ArticleRepository;
import com.ckfinder.demo.repository.ReadLaterArticleRepository;
import com.ckfinder.demo.repository.ReadLaterRepository;
import com.ckfinder.demo.service.inter.IReadLaterArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReadLaterArticleService implements IReadLaterArticleService{

    @Autowired
    private ReadLaterArticleRepository readLaterArticleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ReadLaterRepository readLaterRepository;

    @Override
    public ReadLaterArticleEntity create(Long readLaterId, Long articleId) {
//
        ReadLaterArticleEntity entity = new ReadLaterArticleEntity();
        entity.setReadLaterEntity(readLaterRepository.findById(readLaterId).get());
        entity.setArticleEntity(articleRepository.findById(articleId).get());
        return readLaterArticleRepository.save(entity);
    }

    @Override
    public Optional<ReadLaterArticleEntity> findByReadLaterIdAndArticleId(Long readLaterId, Long articleId) {
        return readLaterArticleRepository.findByReadLaterEntity_IdAndArticleEntity_Id(readLaterId,articleId);
    }
}
