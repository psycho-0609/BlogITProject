package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.CommentDTO;
import com.finnal.blogit.dto.response.ListArticleComment;
import com.finnal.blogit.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICommentService {

    CommentEntity save(CommentEntity entity);
    List<CommentDTO> getAllByArticleId(Long id);
    Optional<CommentEntity> findById(Long id);
    void deleteById(Long id);
    Boolean existsById(Long id);
    List<ListArticleComment> listTotalComment(List<Long> ids);
    List<ListArticleComment> findByTitle(String title);
    Page<Long> getListArticleId(Pageable pageable, String title);
    Long countListArticle(String title);


}
