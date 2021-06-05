package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.TopicEntity;

import java.util.List;
import java.util.Optional;

public interface ITopicService {

    TopicEntity save(TopicEntity entity);
    void delete(Integer id);
    Optional<TopicEntity> findById(Integer id);
    List<TopicEntity> findAll();
}
