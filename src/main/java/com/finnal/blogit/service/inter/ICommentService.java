package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.CommentDTO;
import com.finnal.blogit.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface ICommentService {

    CommentEntity save(CommentEntity entity);
    List<CommentDTO> getAllByArticleId(Long id);
    Optional<CommentEntity> findById(Long id);
    void deleteById(Long id);
    Boolean existsById(Long id);

}
