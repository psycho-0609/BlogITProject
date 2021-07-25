package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.ReplyCommentDTO;
import com.finnal.blogit.entity.ReplyCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<ReplyCommentEntity, Long> {

    @Query("select new com.finnal.blogit.dto.response.ReplyCommentDTO" +
            "(r.id, r.comment.id, r.content, r.createdDate," +
            "r.account.id, r.account.email, r.account.status," +
            "r.account.userDetailEntity.id, r.account.userDetailEntity.firstName, " +
            "r.account.userDetailEntity.lastName, r.account.userDetailEntity.thumbnail) " +
            "from ReplyCommentEntity r where r.comment.article.id = :id order by r.createdDate desc ")
    List<ReplyCommentDTO> findAllByArticleId(@Param("id") Long id);
}
