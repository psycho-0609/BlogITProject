package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.request.TopicRequest;
import com.finnal.blogit.dto.response.TopicResponse;
import com.finnal.blogit.entity.TopicEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic")
public class TopicAPI {

    @Autowired
    private ITopicService topicService;

    @PostMapping("/create")
    public ResponseEntity<TopicResponse> createOrUpdate(@RequestBody TopicRequest request) throws APIException {
        validate(request);
        TopicEntity topicEntity;
        if(request.getId() != null){
            topicEntity = topicService.findById(request.getId()).orElseThrow( () -> new ItemNotFoundException("Topic not found"));
        }else{
            topicEntity = new TopicEntity();
        }
        setParam(topicEntity,request);
        TopicEntity topicSaved = topicService.save(topicEntity);
        return new ResponseEntity<>(new TopicResponse(topicSaved.getId(),topicSaved.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TopicResponse> delete(@PathVariable Integer id) throws APIException{
        TopicEntity entity = topicService.findById(id).orElseThrow(() -> new ItemNotFoundException("Topic not found"));
        topicService.delete(id);
        return new ResponseEntity<>(new TopicResponse(entity.getId(),entity.getName()), HttpStatus.OK);
    }

    private void validate(TopicRequest request) throws APIException {
        if(request.getName() == null || request.getName().isEmpty()){
            throw new ItemCannotEmptyException("Name cannot empty");
        }
        if(request.getShortDescription() == null || request.getShortDescription().isEmpty()){
            throw new ItemCannotEmptyException("Description cannot empty");
        }

    }

    private void setParam(TopicEntity entity ,TopicRequest request){
        entity.setName(request.getName());
        entity.setShortDescription(request.getShortDescription());
    }
}
