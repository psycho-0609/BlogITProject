package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.dto.request.ReplyCommentRequest;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.ReplyCommentDTO;
import com.finnal.blogit.entity.CommentEntity;
import com.finnal.blogit.entity.ReplyCommentEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.ICommentService;
import com.finnal.blogit.service.inter.IReplyCommentService;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user/replyComment")
public class ReplyComment {

    @Autowired
    private IReplyCommentService service;

    @Autowired
    private ICommentService cService;

    @Autowired
    private IUserAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> create(@RequestBody ReplyCommentRequest request) throws APIException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        ReplyCommentEntity entity;
        if(request.getId() != null){
            validateDataToUpdate(request);
            entity = service.findById(request.getId()).orElseThrow(()-> new ItemNotFoundException("Comment not found"));
            setParam(request,entity,userDetail.getId());
            service.save(entity);
            return new ResponseEntity<>(new MessageDTO("Update successfully"), HttpStatus.OK);
        }else{
            entity = new ReplyCommentEntity();
            validateDataToCreate(request);
            setParam(request, entity, userDetail.getId());
            service.save(entity);
            return new ResponseEntity<>(new MessageDTO("Create successfully"), HttpStatus.OK);
        }

    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ReplyCommentDTO> getOneById(@PathVariable("id") Long id) throws APIException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        ReplyCommentDTO replyComment = service.findOneById(id).orElseThrow(()->new ItemNotFoundException("Reply is not found"));
        if(!replyComment.getAccount().getId().equals(userDetail.getId())){
            throw new ItemNotFoundException("Reply is not found");
        }
        return new ResponseEntity<>(replyComment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") Long id) throws APIException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        ReplyCommentEntity entity = service.findById(id).orElseThrow(()-> new ItemNotFoundException("Comment not found"));
        if(!entity.getAccount().getId().equals(userDetail.getId())){
            throw new ItemCanNotModifyException("Cannot delete this comment");
        }
        service.deleteById(id);
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }
    private void validateDataToCreate(ReplyCommentRequest request) throws APIException {
        if(request.getCommentId() == null){
            throw new ItemCannotEmptyException("Comment Id cannot empty");
        }
        if(request.getContent() == null || Strings.isEmpty(request.getContent())){
            throw new ItemCannotEmptyException("Content cannot empty");
        }
        if(!cService.existsById(request.getCommentId())){
            throw new ItemNotFoundException("Comment to reply not found");
        }
        if(request.getRepAccountId() != null){
            if(!accountService.existsById(request.getRepAccountId())){
                throw new ItemNotFoundException("User to reply not found");
            }
        }
    }
    private void validateDataToUpdate(ReplyCommentRequest request) throws APIException {
        if(request.getContent() == null || Strings.isEmpty(request.getContent())){
            throw new ItemCannotEmptyException("Content cannot empty");
        }
    }

    private void setParam(ReplyCommentRequest request, ReplyCommentEntity entity, Long accountId){
        if(request.getId() == null){
            entity.setAccount(new UserAccountEntity(accountId));
            entity.setComment(new CommentEntity(request.getCommentId()));
            entity.setCreatedDate(LocalDateTime.now());
        }
        if(request.getRepAccountId() != null){
            entity.setRepAccount(new UserAccountEntity(request.getRepAccountId()));
        }else{
            entity.setRepAccount(null);
        }
        entity.setContent(request.getContent());
    }

}
