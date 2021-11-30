package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.GetListBookMark;
import com.finnal.blogit.entity.BookMarkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMarkEntity,Long> {
    

    @Query("select rl from BookMarkEntity rl where rl.account.id =:acountId and rl.articleEntity.id = :articleId")
    Optional<BookMarkEntity> findByAccount_IdAndArticleEntity_Id(@Param("acountId") Long accountId, @Param("articleId") Long articleId);

//    @Query("select new com.finnal.blogit.dto.response.GetListReadLater(h.id, h.articleEntity.id, h.articleEntity.title, h.articleEntity.published," +
//            "h.articleEntity.news, h.articleEntity.status, h.articleEntity.countView, h.articleEntity.image, h.articleEntity.shortDescription," +
//            "h.articleEntity.createdDate, h.articleEntity.publishedDate, h.articleEntity.modifiedDate, h.articleEntity.prioritize, h.articleEntity.topic.id, h.articleEntity.topic.name," +
//            "h.articleEntity.userAccount.id, h.articleEntity.userAccount.email, h.articleEntity.userAccount.userDetailEntity.id, h.articleEntity.userAccount.userDetailEntity.firstName, h.articleEntity.userAccount.userDetailEntity.lastName," +
//            "h.articleEntity.userAccount.userDetailEntity.thumbnail) from ReadLaterArticleEntity as h where h.readLaterEntity.id =:id order by h.createdDate desc")
//    List<GetListReadLater> getListReadLaterByReadLaterId(@Param("id") Long id);

//    @Query("select new com.finnal.blogit.dto.response.GetListReadLater(h.id, h.articleEntity.id, h.articleEntity.title, h.articleEntity.published," +
//            "h.articleEntity.news, h.articleEntity.status, h.articleEntity.countView, h.articleEntity.image, h.articleEntity.shortDescription," +
//            "h.articleEntity.createdDate, h.articleEntity.publishedDate, h.articleEntity.modifiedDate, h.articleEntity.prioritize, h.articleEntity.topic.id, h.articleEntity.topic.name," +
//            "h.articleEntity.userAccount.id, h.articleEntity.userAccount.email, h.articleEntity.userAccount.userDetailEntity.id, h.articleEntity.userAccount.userDetailEntity.firstName, h.articleEntity.userAccount.userDetailEntity.lastName," +
//            "h.articleEntity.userAccount.userDetailEntity.thumbnail) from ReadLaterArticleEntity as h where h.account.id =:id order by h.createdDate desc")
//    List<GetListReadLater> getListReadLaterByAccountId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.GetListBookMark(h.id, h.articleEntity.id, h.articleEntity.title, h.articleEntity.published," +
            "h.articleEntity.news, h.articleEntity.status, h.articleEntity.countView, h.articleEntity.image, h.articleEntity.shortDescription," +
            "h.articleEntity.createdDate, h.articleEntity.publishedDate, h.articleEntity.modifiedDate, h.articleEntity.prioritize, h.articleEntity.topic.id, h.articleEntity.topic.name," +
            "h.articleEntity.userAccount.id, h.articleEntity.userAccount.email, h.articleEntity.userAccount.userDetailEntity.id, h.articleEntity.userAccount.userDetailEntity.firstName, h.articleEntity.userAccount.userDetailEntity.lastName," +
            "h.articleEntity.userAccount.userDetailEntity.thumbnail) from BookMarkEntity as h where h.id in :ids")
    List<GetListBookMark> getListReadLaterByAccountId(@Param("ids") List<Long> ids);

    Long countByArticleEntityId(Long id);

    @Query("select rl.id from BookMarkEntity rl where rl.account.id =:userId and rl.articleEntity.title like %:title%")
    Page<Long> getByUsaerId(Pageable pageable, @Param("userId") Long userId, @Param("title") String title);

    Long countAllByAccountId(Long userId);

    @Query("select count(rl) from BookMarkEntity rl where rl.articleEntity.published = 1 and rl.articleEntity.status = 1 and rl.articleEntity.userAccount.id = :id")
    Long countTotalOfUser(@Param("id") Long userId);
}

