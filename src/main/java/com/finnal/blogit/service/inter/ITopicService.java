package com.finnal.blogit.service.inter;

import com.finnal.blogit.entity.TopicEntity;

import java.util.List;
import java.util.Optional;

public interface ITopicService {

    TopicEntity save(TopicEntity entity);
    void delete(Integer id);
    Optional<TopicEntity> findById(Integer id);
    List<TopicEntity> findAll();
}
