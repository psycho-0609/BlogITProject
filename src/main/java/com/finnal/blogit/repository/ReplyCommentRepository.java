package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.ReplyCommentDTO;
import com.finnal.blogit.dto.response.TotalReplyComment;
import com.finnal.blogit.entity.ReplyCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyCommentRepository extends JpaRepository<ReplyCommentEntity, Long> {

    @Query("select new com.finnal.blogit.dto.response.ReplyCommentDTO" +
            "(r.id, r.comment.id, r.content, r.createdDate," +
            "r.account.id, r.account.email, r.account.status," +
            "r.account.userDetailEntity.id, r.account.userDetailEntity.firstName, " +
            "r.account.userDetailEntity.lastName, r.account.userDetailEntity.thumbnail, r.repAccount.id, r.repAccount.userDetailEntity.firstName, r.repAccount.userDetailEntity.lastName) " +
            "from ReplyCommentEntity r left join UserAccountEntity  as u on r.account.id = u.id and r.repAccount.id = u.id " +
            "left join UserDetailEntity ud on r.account.userDetailEntity.id = ud.userAccount.id  and r.repAccount.userDetailEntity.id = ud.userAccount.id " +
            "where r.comment.article.id = :id order by r.createdDate asc ")
    List<ReplyCommentDTO> findAllByArticleId(@Param("id") Long id);

    @Query("select new com.finnal.blogit.dto.response.TotalReplyComment(r.comment.article.id, count (r)) from ReplyCommentEntity r group by r.comment.article.id")
    List<TotalReplyComment> getTotalComment();

    @Query("select new com.finnal.blogit.dto.response.ReplyCommentDTO" +
            "(r.id, r.comment.id, r.content, r.createdDate," +
            "r.account.id, r.account.email, r.account.status," +
            "r.account.userDetailEntity.id, r.account.userDetailEntity.firstName, " +
            "r.account.userDetailEntity.lastName, r.account.userDetailEntity.thumbnail, r.repAccount.id, r.repAccount.userDetailEntity.firstName, r.repAccount.userDetailEntity.lastName) " +
            "from ReplyCommentEntity r left join UserAccountEntity  as u on r.account.id = u.id and r.repAccount.id = u.id " +
            "left join UserDetailEntity ud on r.account.userDetailEntity.id = ud.userAccount.id  and r.repAccount.userDetailEntity.id = ud.userAccount.id " +
            "where r.id =:id ")
    Optional<ReplyCommentDTO> getOneById(@Param("id") Long id);
}
