package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.GetFavArticle;
import com.finnal.blogit.entity.FavoriteArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavouriteArticleRepository extends JpaRepository<FavoriteArticleEntity,Long> {

//    Optional<FavoriteArticleEntity> findByFavoriteEntity_IdAndArticleEntity_Id(Long FavId, Long ArticleId);

    Optional<FavoriteArticleEntity> findByAccount_IdAndArticleEntity_Id(Long accountId, Long articleId);
    Long countAllByArticleEntityId(Long id);

//    @Query("select new com.finnal.blogit.dto.response.GetFavArticle(fa.id, fa.articleEntity.id, fa.articleEntity.title, fa.articleEntity.published, fa.articleEntity.news, fa.articleEntity.status," +
//            "fa.articleEntity.countView, fa.articleEntity.image, fa.articleEntity.shortDescription, fa.articleEntity.createdDate, fa.articleEntity.publishedDate," +
//            "fa.articleEntity.modifiedDate, fa.articleEntity.prioritize, fa.articleEntity.topic.id, fa.articleEntity.topic.name, fa.articleEntity.userAccount.id, fa.articleEntity.userAccount.email," +
//            "fa.articleEntity.userAccount.userDetailEntity.id, fa.articleEntity.userAccount.userDetailEntity.firstName, fa.articleEntity.userAccount.userDetailEntity.lastName," +
//            "fa.articleEntity.userAccount.userDetailEntity.thumbnail) from FavoriteArticleEntity as fa where fa.favoriteEntity.id =:id order by fa.createdDate desc")
//    List<GetFavArticle>  getListFavByAccountId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.GetFavArticle(fa.id, fa.articleEntity.id, fa.articleEntity.title, fa.articleEntity.published, fa.articleEntity.news, fa.articleEntity.status," +
            "fa.articleEntity.countView, fa.articleEntity.image, fa.articleEntity.shortDescription, fa.articleEntity.createdDate, fa.articleEntity.publishedDate," +
            "fa.articleEntity.modifiedDate, fa.articleEntity.prioritize, fa.articleEntity.topic.id, fa.articleEntity.topic.name, fa.articleEntity.userAccount.id, fa.articleEntity.userAccount.email," +
            "fa.articleEntity.userAccount.userDetailEntity.id, fa.articleEntity.userAccount.userDetailEntity.firstName, fa.articleEntity.userAccount.userDetailEntity.lastName," +
            "fa.articleEntity.userAccount.userDetailEntity.thumbnail) from FavoriteArticleEntity as fa where fa.account.id =:id order by fa.createdDate desc")
    List<GetFavArticle>  getListFavByAccountId(@Param("id") Long id);

    @Query("select count(fa) from FavoriteArticleEntity as fa where fa.articleEntity.id in :ids")
    Long countTotalFavorite(@Param("ids") List<Long> ids);




}
