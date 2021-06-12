package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.ReadLaterArticleEntity;
import com.finnal.blogit.repository.ArticleRepository;
import com.finnal.blogit.repository.ReadLaterArticleRepository;
import com.finnal.blogit.repository.ReadLaterRepository;
import com.finnal.blogit.service.inter.IReadLaterArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        ReadLaterArticleEntity entity;
        Optional<ReadLaterArticleEntity> readLaterArticleOp = readLaterArticleRepository.findByReadLaterEntity_IdAndArticleEntity_Id(readLaterId, articleId);
        if(readLaterArticleOp.isPresent()){
            entity = readLaterArticleOp.get();
        }else{
            entity = new ReadLaterArticleEntity();
            entity.setReadLaterEntity(readLaterRepository.findById(readLaterId).get());
            entity.setArticleEntity(articleRepository.findById(articleId).get());
        }
        entity.setCreatedDate(LocalDateTime.now());
        return readLaterArticleRepository.save(entity);
    }

    @Override
    public Optional<ReadLaterArticleEntity> findByReadLaterIdAndArticleId(Long readLaterId, Long articleId) {
        return readLaterArticleRepository.findByReadLaterEntity_IdAndArticleEntity_Id(readLaterId,articleId);
    }

    @Override
    public Optional<ReadLaterArticleEntity> findById(Long id) {
        return readLaterArticleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        readLaterArticleRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        readLaterArticleRepository.deleteAll();
    }

    @Override
    public Long countItemByReadLaterId(Long id) {
        return readLaterArticleRepository.countAllByReadLaterEntityId(id);
    }
}
