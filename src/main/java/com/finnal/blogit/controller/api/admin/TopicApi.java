package com.finnal.blogit.controller.api.admin;

import com.finnal.blogit.dto.request.TopicRequest;
import com.finnal.blogit.dto.response.CustomTopicDTO;
import com.finnal.blogit.dto.response.MessageDTO;

import com.finnal.blogit.entity.TopicEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemExitedException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.ITopicService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/category")
public class TopicApi {

    @Autowired
    private ITopicService topicService;

    @PostMapping("/edit")
    public ResponseEntity<MessageDTO> createOrEdit(@RequestBody TopicRequest request) throws APIException{
        TopicEntity entity;
        validate(request);
        if(request.getId() != null){
            entity = topicService.findById(request.getId()).orElseThrow(()-> new ItemNotFoundException("Category is not found"));
            validateForUpdate(entity,request);
            setParam(entity,request);
            topicService.save(entity);
            return new ResponseEntity<>(new MessageDTO("Update category successfully"), HttpStatus.OK);
        }else{
            entity = new TopicEntity();
            validateForCreate(request);
            setParam(entity,request);
            topicService.save(entity);

            return new ResponseEntity<>(new MessageDTO("Create category successfully"), HttpStatus.OK);
        }

    }

    private void validate(TopicRequest request) throws APIException {
        if(request.getName() == null || Strings.isEmpty(request.getName())){
            throw new ItemCannotEmptyException("Name is required");
        }
        if(request.getShortDescription() == null || Strings.isEmpty(request.getShortDescription())){
            throw new ItemCannotEmptyException("Short Description is required");
        }
    }

    private void validateForCreate(TopicRequest request) throws APIException{
        Optional<TopicEntity> entity = topicService.findByName(request.getName());
        if(entity.isPresent()){
            throw new ItemExitedException("Category is existed");
        }
    }

    private void validateForUpdate(TopicEntity entity, TopicRequest request) throws APIException{
        if(!entity.getName().equals(request.getName())){
            Optional<TopicEntity> entityOp = topicService.findByName(request.getName());
            if(entityOp.isPresent()){
                throw new ItemExitedException("Category is existed");
            }
        }
    }

    private TopicEntity setParam(TopicEntity entity,TopicRequest request){
        entity.setName(request.getName());
        entity.setShortDescription(request.getShortDescription());
        return entity;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomTopicDTO>> getAll(){
        return new ResponseEntity<>(topicService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") Integer id) throws APIException{
        TopicEntity topicEntity = topicService.findById(id).orElseThrow(()-> new ItemNotFoundException("Category is not found"));
        topicService.delete(topicEntity.getId());
        return new ResponseEntity<>(new MessageDTO("Delete category successfully"), HttpStatus.OK);
    }
}
