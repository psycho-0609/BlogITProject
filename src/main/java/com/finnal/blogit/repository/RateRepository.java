package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.RateDTO;
import com.finnal.blogit.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Long> {

    Optional<RateEntity> findByAccountIdAndArticleId(Long accountId, Long articleId);
    Long countByArticleId(Long id);

    @Query(value = "select sum(r.score) from rate as r where r.article_id = :id", nativeQuery = true)
    Long sumScore(Long id);

    @Query("select new com.finnal.blogit.dto.response.RateDTO(r.id, r.article.id, r.score) from RateEntity r where r.account.id = :accountId and r.article.id = :articleId")
    Optional<RateDTO> findOneByAccountIdAndArticleId(@Param("accountId") Long accountId, @Param("articleId") Long articleId);
}
