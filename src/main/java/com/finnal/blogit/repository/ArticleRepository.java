package com.finnal.blogit.repository;


import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {

//    @Query("select new com.ckfinder.demo.dto.response.CustomArticleDTO() from")
//    List<CustomArticleDTO> find();
    List<ArticleEntity> findAllByStatusAndPublishedAndUserAccountId(ArticleStatus status, ArticlePublished published, Long id);
    List<ArticleEntity> findAllByStatusAndUserAccountId(ArticleStatus status,Long id);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.published = :published and a.userAccount.id = :id and a.status = :status order by a.modifiedDate desc, a.createdDate desc")
    List<CustomArticleDTO> findAllByPublishedStatusAndUserAccountId( @Param("published") ArticlePublished published, @Param("id") Long id, @Param("status") ArticleStatus status);


    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.userAccount.id = :id order by a.modifiedDate desc, a.createdDate desc ")
    List<CustomArticleDTO> findAllByAccountId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.id = :id")
    CustomArticleDTO findByArticleId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.title like %:title% and a.published = :published and a.userAccount.id = :id and a.status = :status order by a.modifiedDate desc, a.createdDate desc")
    List<CustomArticleDTO> findAllForSearch( @Param("published") ArticlePublished published, @Param("id") Long id, @Param("status") ArticleStatus status, @Param("title") String title);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.published = :published and a.status = :status order by a.modifiedDate desc, a.createdDate desc")
    List<CustomArticleDTO> findAllByPublishedAndStatus(@Param("published") ArticlePublished published, @Param("status") ArticleStatus status);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.published = :published and a.status = :status and a.topic.id =:topicId order by a.publishedDate desc")
    List<CustomArticleDTO> findByTopicId(@Param("published") ArticlePublished published, @Param("status") ArticleStatus status, @Param("topicId") Integer id);

    @Query("select sum(a.countView)  from ArticleEntity as a where a.id in :ids")
    Long totalCountView(@Param("ids") List<Long> listId);

    @Query("select a from ArticleEntity as a where a.published = 1 and a.status = 1 and a.prioritize >= 1 and a.prioritize <= 4 order by a.prioritize asc")
    List<ArticleEntity> findAllByPrioritize();

    @Query("select a from ArticleEntity as a where a.published = 1 and a.status = 1 order by a.countView desc")
    List<ArticleEntity> findForPopular();

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.status = :status order by a.modifiedDate desc")
    List<CustomArticleDTO> findByStatus(@Param("status") ArticleStatus status);

    @Query("select new com.finnal.blogit.dto.response.ArticleCustomDTO(a.id, a.title, a.published, a.prioritize) from ArticleEntity as a where a.id =:id")
    Optional<ArticleCustomDTO> getById(@Param("id") Long id);

    Optional<ArticleEntity> findByPrioritize(Integer pro);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.published = :published and a.status = :status order by a.publishedDate desc")
    List<CustomArticleDTO> findAllByPublishedAndStatusForWeb(@Param("published") ArticlePublished published, @Param("status") ArticleStatus status);

    @Query("select a from ArticleEntity as a where a.published = :published and a.status = :status order by a.favoriteArticle.size desc")
    List<ArticleEntity> getAllOderByFavCount(@Param("published") ArticlePublished published, @Param("status") ArticleStatus status);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.id in (:ids)")
    List<CustomArticleDTO> getListArticle(@Param("ids") List<Long> ids);

    @Query("select count(a) from ArticleEntity a where a.status = 1 and a.published = 1")
    Long countAllByStatusAndPublished();

    @Query(value = "select count(*) FROM blogit.article AS a where month(a.created_date) = :month and a.published = 1 and a.status = 1", nativeQuery = true)
    Long countByMonth(Integer month);

    @Query(value = "SELECT count(*) FROM blogit.article as a inner join blogit.topic as t on a.topic_id = t.id where a.published = 1 and a.status = 1 and month(a.published_date) = :month and t.id = :topicId", nativeQuery = true)
    Long countAllByTopicAndMonth(@Param("month") Integer month, @Param("topicId") Integer id);
}
