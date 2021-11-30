package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.response.StatisticAuthor;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.repository.CommentRepository;
import com.finnal.blogit.repository.UserAccountRepository;
import com.finnal.blogit.service.inter.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @Autowired
    private IArticleService service;

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/statisticAuthor")
    public ResponseEntity<List<StatisticAuthor>> getStatisticAuthor() {
        return new ResponseEntity<>(service.getStatisticAuthor(), HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<UserInforDto>> getAllUser() {
        return new ResponseEntity<>(repository.getAllInforUser(), HttpStatus.OK);
    }

    @GetMapping("/testPanination")
    public ResponseEntity<List<Long>> pagination(@RequestParam("page") Integer page){
        Sort sort = Sort.by("publishedDate").descending();
        Pageable pageable = PageRequest.of(page-1,10, sort);
        Page<Long> pageData = service.getIdForPanination(pageable, ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
        return new ResponseEntity<>(pageData.getContent(), HttpStatus.OK);

    }

    @GetMapping("/getList")
    public ResponseEntity<List<Long>> getList(@RequestParam("title") String title){
        return new ResponseEntity<>(commentRepository.getListTest(title), HttpStatus.OK);
    }

    @GetMapping("/getCount")
    public ResponseEntity<Long> getCount(@RequestParam("title") String title){
        return new ResponseEntity<>(commentRepository.countAllArticle(title), HttpStatus.OK);
    }
}
