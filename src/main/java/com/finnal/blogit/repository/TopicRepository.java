package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.CustomTopicDTO;
import com.finnal.blogit.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<TopicEntity,Integer> {

    Optional<TopicEntity> findByName(String name);

    @Query("select new com.finnal.blogit.dto.response.CustomTopicDTO(t.id, t.name, t.shortDescription) from TopicEntity as t")
    List<CustomTopicDTO> getAll();

}
