package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.CommentDTO;
import com.finnal.blogit.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("select new com.finnal.blogit.dto.response.CommentDTO(c.id, c.content, c.createdDate, c.article.id, " +
            "c.account.id, c.account.email, c.account.status, c.account.userDetailEntity.id, c.account.userDetailEntity.firstName," +
            "c.account.userDetailEntity.lastName, c.account.userDetailEntity.thumbnail) from CommentEntity c where c.article.id =:id order by c.createdDate desc")
    List<CommentDTO> getAllByArticleId(@Param("id") Long articleId);
}