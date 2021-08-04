package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.dto.request.CommentRequest;
import com.finnal.blogit.dto.request.ReplyCommentRequest;
import com.finnal.blogit.dto.response.CommentDTO;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.CommentEntity;
import com.finnal.blogit.entity.ReplyCommentEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ICommentService;
import com.finnal.blogit.service.inter.IReplyCommentService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user/comment")
public class CommentAPI {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICommentService commentService;



    @GetMapping("/getAll")
    public ResponseEntity<List<CommentDTO>> getAllByArticleId(@RequestParam("postId") Long id){
        return new ResponseEntity<>(commentService.getAllByArticleId(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createComment(@RequestBody CommentRequest request) throws APIException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        CommentEntity entity;
        validate(request);
        if (request.getId() != null) {
            entity = commentService.findById(request.getId()).orElseThrow(() -> new ItemNotFoundException("Comment is not found"));
            setParam(entity, request, userDetail.getId());
            commentService.save(entity);
            return new ResponseEntity<>(new MessageDTO("Update success"), HttpStatus.OK);
        } else {
            entity = new CommentEntity();
            setParam(entity,request, userDetail.getId());
            commentService.save(entity);
            return new ResponseEntity<>(new MessageDTO("Comment success"), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> deleteComment(@PathVariable("id") Long id) throws APIException{
        CustomUserDetail userDetails = UserInfor.getPrincipal();
        CommentEntity entity = commentService.findById(id).orElseThrow(()-> new ItemNotFoundException("Comment id not found"));
        if(entity.getAccount().getId() != userDetails.getId()){
            throw new ItemCanNotModifyException("Cannot delete this comment");
        }
        commentService.deleteById(id);
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }

    private void validate(CommentRequest request) throws APIException {
        if (!articleService.isExistedById(request.getArticleId())) {
            throw new ItemNotFoundException("Post it not found");
        }
        if (request.getContent() == null || Strings.isEmpty(request.getContent())) {
            throw new ItemCannotEmptyException("Content cannot empty");
        }
        if (request.getArticleId() == null) {
            throw new ItemCannotEmptyException("Post can not empty");
        }
    }

    private void setParam(CommentEntity entity, CommentRequest request, Long accountId) {
        entity.setContent(request.getContent());
        if (entity.getId() == null) {
            entity.setCreatedDate(LocalDateTime.now());
        }
        entity.setArticle(new ArticleEntity(request.getArticleId()));
        entity.setAccount(new UserAccountEntity(accountId));
    }


}
