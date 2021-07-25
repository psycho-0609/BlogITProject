package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.CommentDTO;
import com.finnal.blogit.dto.response.ReplyCommentDTO;
import com.finnal.blogit.entity.CommentEntity;
import com.finnal.blogit.repository.CommentRepository;
import com.finnal.blogit.repository.ReplyCommentRepository;
import com.finnal.blogit.service.inter.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private ReplyCommentRepository replyCommentRepository;

    @Override
    public CommentEntity save(CommentEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<CommentDTO> getAllByArticleId(Long id) {
        List<ReplyCommentDTO> replyList = replyCommentRepository.findAllByArticleId(id);
        List<CommentDTO> listComment = repository.getAllByArticleId(id);
        Map<Long, List<ReplyCommentDTO>> mapReply = replyList.stream().collect(Collectors.groupingBy(ReplyCommentDTO::getCommentId));
        listComment.stream().forEach(el -> el.setReplyComment(mapReply.get(el.getId())));
        return listComment;
    }

    @Override
    public Optional<CommentEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
