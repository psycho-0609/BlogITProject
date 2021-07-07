package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.CustomTopicDTO;
import com.finnal.blogit.entity.TopicEntity;
import com.finnal.blogit.repository.TopicRepository;
import com.finnal.blogit.service.inter.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService implements ITopicService {

    @Autowired
    private TopicRepository repository;

    @Override
    public TopicEntity save(TopicEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<TopicEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<TopicEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TopicEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<CustomTopicDTO> getAll() {
        return repository.getAll();
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
