package com.finnal.blogit.repository;


import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {

//    @Query("select new com.ckfinder.demo.dto.response.CustomArticleDTO() from")
//    List<CustomArticleDTO> find();
    List<ArticleEntity> findAllByStatusAndPublishedAndUserAccountId(ArticleStatus status, ArticlePublished published, Long id);
    List<ArticleEntity> findAllByStatusAndUserAccountId(ArticleStatus status,Long id);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.published = :published and a.userAccount.id = :id and a.status = :status order by a.modifiedDate desc, a.createdDate desc")
    List<CustomArticleDTO> findAllByPublishedStatusAndUserAccountId( @Param("published") ArticlePublished published, @Param("id") Long id, @Param("status") ArticleStatus status);


    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.userAccount.id = :id")
    List<CustomArticleDTO> findAllByAccountId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.id = :id")
    CustomArticleDTO findByArticleId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.title like %:title% and a.published = :published and a.userAccount.id = :id and a.status = :status order by a.modifiedDate desc, a.createdDate desc")
    List<CustomArticleDTO> findAllForSearch( @Param("published") ArticlePublished published, @Param("id") Long id, @Param("status") ArticleStatus status, @Param("title") String title);

}
