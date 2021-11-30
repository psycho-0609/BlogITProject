package com.finnal.blogit.repository;


import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.dto.response.ListArticleComment;
import com.finnal.blogit.dto.response.StatisticAuthor;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {

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
            "from ArticleEntity as a where a.id = :id")
    CustomArticleDTO findByArticleId(@Param("id") Long id);

    @Query("select sum(a.countView)  from ArticleEntity as a where a.id in :ids")
    Long totalCountView(@Param("ids") List<Long> listId);

    @Query("select a from ArticleEntity as a where a.published = 1 and a.status = 1 and a.prioritize >= 1 and a.prioritize <= 4 order by a.prioritize asc")
    List<ArticleEntity> findAllByPrioritize();

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.published = 1 and a.status = 1 order by a.countView desc")
    List<CustomArticleDTO> findForPopular();

    @Query("select new com.finnal.blogit.dto.response.ArticleCustomDTO(a.id, a.title, a.published, a.prioritize) from ArticleEntity as a where a.id =:id")
    Optional<ArticleCustomDTO> getById(@Param("id") Long id);

    Optional<ArticleEntity> findByPrioritize(Integer pro);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.published = :published and a.status = :status order by a.publishedDate desc")
    List<CustomArticleDTO> findAllByPublishedAndStatusForWeb(@Param("published") ArticlePublished published, @Param("status") ArticleStatus status);

    @Query("select new com.finnal.blogit.dto.response.CustomArticleDTO(a.id, a.title, a.published, a.news, a.status, a.countView," +
            "a.image,a.shortDescription,a.createdDate, a.publishedDate, a.modifiedDate, a.prioritize," +
            "a.topic.id, a.topic.name, a.userAccount.id, a.userAccount.email, a.userAccount.userDetailEntity.id," +
            "a.userAccount.userDetailEntity.firstName, a.userAccount.userDetailEntity.lastName, a.userAccount.userDetailEntity.thumbnail)" +
            "from ArticleEntity as a where a.id in (:ids) order by a.createdDate desc ")
    List<CustomArticleDTO> getListArticle(@Param("ids") List<Long> ids);

    @Query("select count(a) from ArticleEntity a where a.status = 1 and a.published = 1")
    Long countAllByStatusAndPublished();

    @Query(value = "select count(*) FROM article AS a where month(a.published_date) = :month and year(a.published_date) = :year and a.published = 1 and a.status = 1", nativeQuery = true)
    Long countByMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query(value = "SELECT count(*) FROM article as a inner join blogit.topic as t on a.topic_id = t.id where a.published = 1 and a.status = 1 and month(a.published_date) = :month and year (a.published_date) = :year and t.id = :topicId", nativeQuery = true)
    Long countAllByTopicAndMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("topicId") Integer id);

    @Query(value = "select a.user_account_id, count(*) total from article as a where month(a.created_date) between 6 and 8 and a.published = 1 and a.status = 1 group by a.user_account_id",nativeQuery = true)
    List<StatisticAuthor> getStatisticAuthor();

    @Query(value = "select * from article as a where a.topic_Id = :topicId and a.id <> :articleId order by a.published_date desc limit 4", nativeQuery = true)
    List<ArticleEntity> findAllByTopicIdForRelease(@Param("topicId") Integer topicId, @Param("articleId") Long articleId);

    @Query(value = "select * from article as a where a.status = 1 and a.published = 1 order by a.published_date desc limit 4 ", nativeQuery = true)
    List<ArticleEntity> findAllOrderNewsLimit();

    @Query(value = "select * from article as a where a.status = 1 and a.published = 1 order by a.count_view desc limit 4 ", nativeQuery = true)
    List<ArticleEntity> findAllOrderPopularLimit();

    @Query(value = "select * from article as a where a.status = 1 and a.published = 1 order by a.avg_rate desc limit 4 ", nativeQuery = true)
    List<ArticleEntity> findAllOrderRateLimit();

    @Query("select a.id from ArticleEntity a where a.published =:published and a.status = :status")
    Page<Long> getListIdPagination(Pageable pageable, @Param("published") ArticlePublished published, @Param("status") ArticleStatus status);

    @Query("select count (a) from ArticleEntity a where a.published =:published and a.status = :status and a.title like %:title%")
    Long countArticleEntitiesByPublishedAndStatus(@Param("published") ArticlePublished published, @Param("status") ArticleStatus status, @Param("title") String title);
    @Query("select a.id from ArticleEntity a where a.published = 1 and a.status = 1 and a.topic.id = :topicId")
    Page<Long> getListIdByTopicForPagi(Pageable pageable, @Param("topicId") Integer topicId);

    @Query("select count(a)  from ArticleEntity as a where a.published = 1 and a.status = 1 and a.topic.id = :id")
    Long countArticleEntitiesByTopicId(@Param("id") Integer id);

    @Query("select a.id from ArticleEntity a where a.published = :published and a.status = :status and a.title like %:title%")
    Page<Long> getListIdByTitleForPagi(Pageable pageable, @Param("title") String title,@Param("published") ArticlePublished published,@Param("status") ArticleStatus status);

    @Query("select count(a)  from ArticleEntity as a where a.published = :published and a.status = :status and a.title like %:title%")
    Long countArticleByTitle(@Param("title") String title, @Param("published") ArticlePublished published,@Param("status") ArticleStatus status);

    @Query("select a.id from ArticleEntity a where a.title like %:title% and a.published =:published and a.status =:status and  a.userAccount.id = :userId")
    Page<Long> findListIdByPublishedAndStatusOfUser(Pageable pageable, @Param("title") String title, @Param("published") ArticlePublished published, @Param("status") ArticleStatus status, @Param("userId") Long userId);

    @Query("select a.id from ArticleEntity a where a.title like %:title% and a.userAccount.id = :userId and a.status = 2")
    Page<Long> findListIdPrivateOfUser(Pageable pageable, @Param("title") String title, @Param("userId") Long userId);

    @Query("select count(a) from ArticleEntity a where a.status =:status and a.userAccount.id =:userId and a.title like %:title%")
    Long countAllByStatusAndUserAccountId(@Param("status") ArticleStatus status,@Param("userId") Long userId, @Param("title") String title);

    @Query("select count(a) from ArticleEntity a where a.status =:status and a.published = :published and a.userAccount.id =:userId and a.title like %:title%")
    Long countAllByStatusAndPublishedAndUserAccountId(@Param("status") ArticleStatus status, @Param("published") ArticlePublished published, @Param("userId") Long userId, @Param("title") String title);

    @Query("select a.id from ArticleEntity a where a.status =:status and a.title like %:title%")
    Page<Long> getListIdByStatusAndTitleForPagi(Pageable pageable, @Param("status") ArticleStatus status,@Param("title") String title);

    @Query("select count(a) from ArticleEntity a where a.status = :status and a.title like %:title%")
    Long countAllByStatusAndTAndTitleLike(@Param("status") ArticleStatus status, @Param("title") String title);

    @Query("select a.id from ArticleEntity a inner join ArticleReportEntity ar on a.id = ar.articleEntity.id and a.status = 1 order by ar.createdDate desc")
    Page<Long> getListIdForReport(Pageable pageable);

    @Query("select count(a) from ArticleEntity a inner join ArticleReportEntity ar on a.id = ar.articleEntity.id and a.status = 1")
    Long countAllForReport();

    @Query("select count(a) from ArticleEntity a inner join CommentEntity c on a.id = c.article.id and a.status = 1 and a.title like %:title%")
    Long countAllForComment(@Param("title") String title);


}
