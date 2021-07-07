package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.GetListReport;
import com.finnal.blogit.dto.response.HistoryArticleDTO;
import com.finnal.blogit.entity.HistoryArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HistoryArticleRepository extends JpaRepository<HistoryArticleEntity,Long> {
    void deleteAllByHistoryEntity_Id(Long id);
    Optional<HistoryArticleEntity> findByHistoryEntity_IdAndArticleEntity_IdAndCratedDate(Long hisId, Long articleId, Date date);
    Long countAllByHistoryEntityId(Long id);

    @Query("select new com.finnal.blogit.dto.response.HistoryArticleDTO(h.id, h.cratedDate, h.articleEntity.id, h.articleEntity.title, h.articleEntity.published," +
            "h.articleEntity.news, h.articleEntity.status, h.articleEntity.countView, h.articleEntity.image, h.articleEntity.shortDescription," +
            "h.articleEntity.createdDate, h.articleEntity.publishedDate, h.articleEntity.modifiedDate, h.articleEntity.prioritize, h.articleEntity.topic.id, h.articleEntity.topic.name," +
            "h.articleEntity.userAccount.id, h.articleEntity.userAccount.email, h.articleEntity.userAccount.userDetailEntity.id, h.articleEntity.userAccount.userDetailEntity.firstName, h.articleEntity.userAccount.userDetailEntity.lastName," +
            "h.articleEntity.userAccount.userDetailEntity.thumbnail) from HistoryArticleEntity as h where h.historyEntity.id =:id order by h.timeWatch desc")
    List<HistoryArticleDTO> findAllByHistoryEntityId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.HistoryArticleDTO(h.id, h.cratedDate, h.articleEntity.id, h.articleEntity.title, h.articleEntity.published," +
            "h.articleEntity.news, h.articleEntity.status, h.articleEntity.countView, h.articleEntity.image, h.articleEntity.shortDescription," +
            "h.articleEntity.createdDate, h.articleEntity.publishedDate, h.articleEntity.modifiedDate,h.articleEntity.prioritize, h.articleEntity.topic.id, h.articleEntity.topic.name," +
            "h.articleEntity.userAccount.id, h.articleEntity.userAccount.email, h.articleEntity.userAccount.userDetailEntity.id, h.articleEntity.userAccount.userDetailEntity.firstName, h.articleEntity.userAccount.userDetailEntity.lastName," +
            "h.articleEntity.userAccount.userDetailEntity.thumbnail) from HistoryArticleEntity as h where h.historyEntity.id =:id and h.articleEntity.title like %:title% order by h.timeWatch desc")
    List<HistoryArticleDTO> findAllForSearch(@Param("id") Long id, @Param("title") String title);





}
