package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.ReplyCommentEntity;
import com.finnal.blogit.repository.ReplyCommentRepository;
import com.finnal.blogit.service.inter.IReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplyCommentService implements IReplyCommentService {

    @Autowired
    private ReplyCommentRepository repository;

    @Override
    public ReplyCommentEntity save(ReplyCommentEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<ReplyCommentEntity> findById(Long id) {
        return repository.findById(id);
    }
}
