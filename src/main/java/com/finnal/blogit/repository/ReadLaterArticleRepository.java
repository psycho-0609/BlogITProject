package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.GetListReadLater;
import com.finnal.blogit.entity.ReadLaterArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReadLaterArticleRepository extends JpaRepository<ReadLaterArticleEntity,Long> {

//    Optional<ReadLaterArticleEntity> findByReadLaterEntity_IdAndArticleEntity_Id(Long readLaterId, Long articleId);
    Long countAllByReadLaterEntityId(Long id);

    @Query("select rl from ReadLaterArticleEntity rl where rl.account.id =:acountId and rl.articleEntity.id = :articleId")
    Optional<ReadLaterArticleEntity> findByAccount_IdAndArticleEntity_Id(@Param("acountId") Long accountId, @Param("articleId") Long articleId);

//    @Query("select new com.finnal.blogit.dto.response.GetListReadLater(h.id, h.articleEntity.id, h.articleEntity.title, h.articleEntity.published," +
//            "h.articleEntity.news, h.articleEntity.status, h.articleEntity.countView, h.articleEntity.image, h.articleEntity.shortDescription," +
//            "h.articleEntity.createdDate, h.articleEntity.publishedDate, h.articleEntity.modifiedDate, h.articleEntity.prioritize, h.articleEntity.topic.id, h.articleEntity.topic.name," +
//            "h.articleEntity.userAccount.id, h.articleEntity.userAccount.email, h.articleEntity.userAccount.userDetailEntity.id, h.articleEntity.userAccount.userDetailEntity.firstName, h.articleEntity.userAccount.userDetailEntity.lastName," +
//            "h.articleEntity.userAccount.userDetailEntity.thumbnail) from ReadLaterArticleEntity as h where h.readLaterEntity.id =:id order by h.createdDate desc")
//    List<GetListReadLater> getListReadLaterByReadLaterId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.GetListReadLater(h.id, h.articleEntity.id, h.articleEntity.title, h.articleEntity.published," +
            "h.articleEntity.news, h.articleEntity.status, h.articleEntity.countView, h.articleEntity.image, h.articleEntity.shortDescription," +
            "h.articleEntity.createdDate, h.articleEntity.publishedDate, h.articleEntity.modifiedDate, h.articleEntity.prioritize, h.articleEntity.topic.id, h.articleEntity.topic.name," +
            "h.articleEntity.userAccount.id, h.articleEntity.userAccount.email, h.articleEntity.userAccount.userDetailEntity.id, h.articleEntity.userAccount.userDetailEntity.firstName, h.articleEntity.userAccount.userDetailEntity.lastName," +
            "h.articleEntity.userAccount.userDetailEntity.thumbnail) from ReadLaterArticleEntity as h where h.account.id =:id order by h.createdDate desc")
    List<GetListReadLater> getListReadLaterByAccountId(@Param("id") Long id);

    Long countByArticleEntityId(Long id);
}

