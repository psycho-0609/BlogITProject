package com.finnal.blogit.controller.api.admin;

import com.finnal.blogit.dto.request.ArticleChangeStatusRequest;
import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IArticleService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/post/")
public class ArticleAdminAPI {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleCustomDTO> getOne(@PathVariable("id") Long id) throws APIException {
        if(id == null){
            throw new ItemCannotEmptyException("ID cannot null");
        }

        return new ResponseEntity<>(articleService.getById(id).orElseThrow(()-> new ItemNotFoundException("Article is not found")), HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<MessageDTO> changeStatus(@RequestBody ArticleChangeStatusRequest request) throws APIException{
        if(request.getId() == null){
            throw new ItemCannotEmptyException("Id cannot null");
        }
        if(request.getPublished() == null){
            throw new ItemCannotEmptyException("Status cannot null");
        }
        ArticleEntity entity = articleService.findById(request.getId()).orElseThrow(()-> new ItemNotFoundException("Article is not found"));
        if(ArticlePublished.ENABLE.equals(ArticlePublished.fromValue(request.getPublished()))){
            if(!request.getPrioritize().equals(0)){
                Optional<ArticleEntity> entityOptional = articleService.findByPrioritize(request.getPrioritize());
                if(entityOptional.isPresent()){
                    if(!entityOptional.get().getId().equals(entity.getId())){
                        ArticleEntity entityStatus = entityOptional.get();
                        entityStatus.setPrioritize(0);
                        articleService.save(entityStatus);
                    }

                }
            }
            entity.setPrioritize(request.getPrioritize());
        }
        entity.setPublished(ArticlePublished.fromValue(request.getPublished()));
        entity.setNews(ArticleNew.DISABLE);
        articleService.save(entity);
        return new ResponseEntity<>(new MessageDTO("Your action is successfully"), HttpStatus.OK);
    }

    @GetMapping("/published")
    public ResponseEntity<List<CustomArticleDTO>> getArticlePublished(@RequestParam(value = "title", required = false) String title){
        List<CustomArticleDTO> list = articleService.findByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
        return new ResponseEntity<>(filterItem(list,title), HttpStatus.OK);
    }

    @GetMapping("/unlisted")
    public ResponseEntity<List<CustomArticleDTO>> getArticleUnlisted(@RequestParam(value = "title", required = false) String title){
        List<CustomArticleDTO> list = articleService.findByPublishedAndStatus(ArticlePublished.DISABLE, ArticleStatus.PUBLIC);
        return new ResponseEntity<>(filterItem(list,title), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomArticleDTO>> getAllArticle(@RequestParam(value = "title", required = false) String title){
        List<CustomArticleDTO> list = articleService.findByStatus(ArticleStatus.PUBLIC);
        return new ResponseEntity<>(filterItem(list,title), HttpStatus.OK);
    }

    private List<CustomArticleDTO> filterItem(List<CustomArticleDTO> list, String title){
        if(title != null && !Strings.isEmpty(title)){
            list = list.stream().filter(el -> el.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        }
        return list;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> deleteArticle(@PathVariable("id") Long id) throws APIException{
        ArticleEntity entity = articleService.findById(id).orElseThrow(()-> new ItemNotFoundException("Post is not found"));
        articleService.deleteArticle(entity.getId());
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }


}
