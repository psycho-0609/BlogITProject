package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.response.StatisticAuthor;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.repository.UserAccountRepository;
import com.finnal.blogit.service.inter.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @Autowired
    private IArticleService service;

    @Autowired
    private UserAccountRepository repository;

    @GetMapping("/statisticAuthor")
    public ResponseEntity<List<StatisticAuthor>> getStatisticAuthor() {
        return new ResponseEntity<>(service.getStatisticAuthor(), HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<UserInforDto>> getAllUser() {
        return new ResponseEntity<>(repository.getAllInforUser(), HttpStatus.OK);
    }
}
