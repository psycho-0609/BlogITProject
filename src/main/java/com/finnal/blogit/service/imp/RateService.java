package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.RateDTO;
import com.finnal.blogit.entity.RateEntity;
import com.finnal.blogit.repository.RateRepository;
import com.finnal.blogit.service.inter.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateService implements IRateService {

    @Autowired
    private RateRepository repository;

    @Override
    public RateEntity save(RateEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<RateEntity> findOneByAccountIdAndArticleId(Long accountId, Long articleId) {
        return repository.findByAccountIdAndArticleId(accountId, articleId);
    }

    @Override
    public Long countByArticleId(Long id) {
        return repository.countByArticleId(id);
    }

    @Override
    public Long totalScore(Long id) {
        return repository.sumScore(id);
    }

    @Override
    public Optional<RateDTO> getOneByAccountIdAndArticleId(Long accountId, Long articleId) {
        return repository.findOneByAccountIdAndArticleId(accountId, articleId);
    }
}
