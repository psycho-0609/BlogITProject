package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.dto.response.ArticleResponse;
import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.dto.request.ArticleRequest;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ITopicService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/posts")
public class ArticleUserAPI {


    @Autowired
    private IArticleService articleService;

    @Autowired
    private ITopicService topicService;

    @GetMapping("/allArticle")
    public ResponseEntity<List<CustomArticleDTO>> findAll(){
        return new ResponseEntity<>(articleService.findAllApi(),HttpStatus.OK);
    }

    @GetMapping("/unapproved")
    public ResponseEntity<List<CustomArticleDTO>> findAllUnapprovedPosts(){
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        return new ResponseEntity<>(articleService.findAllByPublishedStatusAndAccount(ArticlePublished.DISABLE,userDetail.getId(), ArticleStatus.PUBLIC), HttpStatus.OK);
    }
    @GetMapping("/private")
    public ResponseEntity<List<CustomArticleDTO>> findAllPrivatePosts(){
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        List<CustomArticleDTO> lists = articleService.findAllByAccountId(userDetail.getId());
        lists = lists.stream().filter(el -> el.getStatus().equals(ArticleStatus.PRIVATE.getValue())).collect(Collectors.toList());
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @GetMapping("/published")
    public ResponseEntity<List<CustomArticleDTO>> findAllPublishedPosts(){
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        return new ResponseEntity<>(articleService.findAllByPublishedStatusAndAccount(ArticlePublished.ENABLE,userDetail.getId(), ArticleStatus.PUBLIC), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ArticleResponse> insertArticle(@RequestBody ArticleRequest request) throws APIException {
        validateEmptyRequest(request);
        return new ResponseEntity<>(new ArticleResponse(articleService.insertArticle(request).getId()), HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<ArticleResponse> updateArticle(@RequestBody ArticleRequest articleRequest) throws APIException {
        Optional<ArticleEntity> articleEntity = articleService.findById(articleRequest.getId());
        if(!articleEntity.isPresent()){
            throw new ItemNotFoundException("Article not found");
        }
        if(!articleEntity.get().getUserAccount().getId().equals(UserInfor.getPrincipal().getId())){
            throw new ItemCanNotModifyException("You cannot modify this article");
        }
        validateEmptyRequest(articleRequest);
        return new ResponseEntity<>( new ArticleResponse(articleService.update(articleRequest).getId()),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomArticleDTO> delete(@PathVariable("id") Long id) throws APIException{
        Optional<ArticleEntity> entityOptional = articleService.findById(id);
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(!entityOptional.isPresent()){
            throw new ItemNotFoundException("Article not found");
        }
        if(userDetail.getId().longValue() != entityOptional.get().getUserAccount().getId().longValue()){
            throw new ItemCanNotModifyException("You cannot modify this article");
        }
        articleService.deleteArticle(id);
        return new ResponseEntity<>(new CustomArticleDTO(id),HttpStatus.OK);
    }

    @PutMapping("/publicStatus")
    public ResponseEntity<CustomArticleDTO> changeStatusPublic(@RequestBody ArticleRequest articleRequest) throws ItemNotFoundException {
        Optional<ArticleEntity> articleOp= articleService.findById(articleRequest.getId());
        if(!articleOp.isPresent()){
            throw new ItemNotFoundException("Not Found Article");
        }
        ArticleEntity articleEntity = articleOp.get();
        articleEntity.setPublished(ArticlePublished.fromValue(articleRequest.getPublished()));
        return new ResponseEntity<>(new CustomArticleDTO(articleService.save(articleEntity).getId()),HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public CustomUserDetail customUserDetail(){
        return UserInfor.getPrincipal();
    }

    @GetMapping("/ckfinder")
    public void CkFinder(HttpServletResponse response){
        response.setHeader("Location","http://localhost:8081/ckfinder/ckfinder.html?type=Images&CKEditor=content&CKEditorFuncNum=3&langCode=vi");
        response.setStatus(302);
    }


    public void validateEmptyRequest(ArticleRequest request) throws APIException{
        if(request.getTitle() == null || request.getTitle().isEmpty()){
            throw new ItemCannotEmptyException("Please input complete information");
        }
        if(request.getShortDescription() == null || request.getShortDescription().isEmpty()){
            throw new ItemCannotEmptyException("Please input complete information");
        }
        if(request.getContent().isEmpty() || request.getContent() == null){
            throw new ItemCannotEmptyException("Please input complete information");
        }
        if(request.getTopicId() == null){
            throw new ItemCannotEmptyException("Please input complete information");
        }
        if(!topicService.findById(request.getTopicId()).isPresent()){
            throw new ItemNotFoundException("Topic not found");
        }
        if(request.getStatus() == null || (!request.getStatus().equals(ArticleStatus.PUBLIC.getValue()) && !request.getStatus().equals(ArticleStatus.PRIVATE.getValue()))){
            throw new ItemCannotEmptyException("Status invalid");
        }

    }


}
