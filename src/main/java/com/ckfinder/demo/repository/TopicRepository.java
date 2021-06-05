package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity,Integer> {

}
