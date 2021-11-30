package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.RateDTO;
import com.finnal.blogit.entity.RateEntity;

import java.util.Optional;

public interface IRateService {
    RateEntity save(RateEntity entity);
    Optional<RateEntity> findOneByAccountIdAndArticleId(Long accountId, Long articleId);
    Long countByArticleId(Long id);
    Long totalScore(Long id);
    Optional<RateDTO> getOneByAccountIdAndArticleId(Long accountId, Long articleId);

}
