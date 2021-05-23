package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.HistoryArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface HistoryArticleRepository extends JpaRepository<HistoryArticleEntity,Long> {
    HistoryArticleEntity findByArticleEntity_IdAndHistoryEntity_Id(Long articleId, Long historyId);
    void deleteAllByHistoryEntity_Id(Long id);
    Optional<HistoryArticleEntity> findByHistoryEntity_IdAndArticleEntity_IdAndCratedDate(Long hisId, Long articleId, Date date);

}
