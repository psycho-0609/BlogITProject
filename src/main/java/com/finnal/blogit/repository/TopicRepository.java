package com.finnal.blogit.repository;

import com.finnal.blogit.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity,Integer> {

}
