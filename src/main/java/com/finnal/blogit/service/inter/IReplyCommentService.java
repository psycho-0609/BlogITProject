package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.ReplyCommentEntity;

import java.util.Optional;

public interface IReplyCommentService {
    ReplyCommentEntity save(ReplyCommentEntity entity);
    Optional<ReplyCommentEntity> findById(Long id);
    void deleteById(Long id);

}
