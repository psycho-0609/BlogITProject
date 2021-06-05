package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.constant.Constant;
import com.ckfinder.demo.dto.response.ArticleResponse;
import com.ckfinder.demo.dto.response.CustomArticleDTO;
import com.ckfinder.demo.exception.api.APIException;
import com.ckfinder.demo.exception.api.ItemCanNotModifyException;
import com.ckfinder.demo.exception.api.ItemCannotEmptyException;
import com.ckfinder.demo.exception.api.ItemNotFoundException;
import com.ckfinder.demo.dto.request.ArticleRequest;
import com.ckfinder.demo.entity.ArticleEntity;
import com.ckfinder.demo.service.inter.IArticleService;
import com.ckfinder.demo.service.inter.ITopicService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/article")
public class ArticleAPI {


    @Autowired
    private IArticleService articleService;

    @Autowired
    private ITopicService topicService;

    @GetMapping("/allArticle")
    public ResponseEntity<List<CustomArticleDTO>> findAll(){
        return new ResponseEntity<>(articleService.findAllApi(),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ArticleResponse> insertArticle(@RequestBody ArticleRequest request) throws APIException{
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
        System.out.println();
        if(!entityOptional.isPresent()){
            throw new ItemNotFoundException("Article not found");
        }
        if(userDetail.getRoleType().intValue() == Constant.USER_TYPE.intValue()){
            if(userDetail.getId().longValue() != entityOptional.get().getUserAccount().getId().longValue()){
                throw new ItemCanNotModifyException("You cannot modify this article");
            }
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
        articleEntity.setIsPublic(articleRequest.getIsPublic());
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
            throw new ItemCannotEmptyException("Please input company information");
        }
        if(!topicService.findById(request.getTopicId()).isPresent()){
            throw new ItemNotFoundException("Topic not found");
        }

    }


}
