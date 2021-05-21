package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.dto.ArticleDTO;
import com.ckfinder.demo.entity.ArticleEntity;
import com.ckfinder.demo.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/article")
public class ArticleAPI {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/allArticle")
    public ResponseEntity<List<ArticleEntity>> findAll(){
        return new ResponseEntity<>(articleService.findAll(),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ArticleEntity> insertArticle(@RequestBody ArticleDTO entity){
        System.out.println("insert data");
        return new ResponseEntity<>(articleService.insertArticle(entity), HttpStatus.OK);
    }
    @PutMapping("/update")
    public ArticleEntity updateArticle(@RequestBody ArticleDTO articleDTO){
        ArticleEntity articleEntity = articleService.update(articleDTO);
        return articleEntity;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ArticleEntity> delete(@PathVariable("id") Long id){
        Optional<ArticleEntity> entityOptional = articleService.findById(id);
        return entityOptional.map(entity ->{
            articleService.deleteArticle(id);
            return new ResponseEntity<>(entity,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/publicStatus")
    public ArticleEntity changeStatusPublic(@RequestBody ArticleDTO articleDTO){
        return articleService.changeStatus(articleDTO);
    }

    @GetMapping("/ckfinder")
    public void CkFinder(HttpServletResponse response){
        response.setHeader("Location","http://localhost:8081/ckfinder/ckfinder.html?type=Images&CKEditor=content&CKEditorFuncNum=3&langCode=vi");
        response.setStatus(302);
    }


}
