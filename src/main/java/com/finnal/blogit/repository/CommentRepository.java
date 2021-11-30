package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.CommentDTO;
import com.finnal.blogit.dto.response.ListArticleComment;
import com.finnal.blogit.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("select new com.finnal.blogit.dto.response.CommentDTO(c.id, c.content, c.createdDate, c.article.id, " +
            "c.account.id, c.account.email, c.account.status, c.account.userDetailEntity.id, c.account.userDetailEntity.firstName," +
            "c.account.userDetailEntity.lastName, c.account.userDetailEntity.thumbnail) from CommentEntity c where c.article.id =:id order by c.createdDate asc ")
    List<CommentDTO> getAllByArticleId(@Param("id") Long articleId);

    @Query("select new com.finnal.blogit.dto.response.ListArticleComment(c.article.id, count (c)) from CommentEntity c where c.article.id in (:ids) group by c.article.id order by c.article.createdDate asc ")
    List<ListArticleComment> countComment(@Param("ids") List<Long> ids);

    @Query("select new com.finnal.blogit.dto.response.ListArticleComment(c.article.id, count (c)) from CommentEntity c where c.article.title like %:title% group by c.article.id order by c.article.createdDate asc ")
    List<ListArticleComment> getAllByLikeTitle(@Param("title") String title);

    @Query("select c.article.id from CommentEntity c where c.article.status = 1 and c.article.title like %:title% group by c.article.id ")
    Page<Long> getListArticleId(Pageable pageable,@Param("title") String title);

    @Query("select count(distinct c.article.id) from CommentEntity c where c.article.title like %:title% and c.article.status = 1")
    Long countAllArticle(@Param("title") String title);

    @Query("select distinct c.article.id from CommentEntity c where c.article.title like %:title% and c.article.status = 1")
    List<Long> getListTest(@Param("title") String title);
}
