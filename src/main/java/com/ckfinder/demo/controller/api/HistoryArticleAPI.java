package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.entity.HistoryArticleEntity;
import com.ckfinder.demo.repository.HistoryArticleRepository;
import com.ckfinder.demo.request.HisFavLaterRequest;
import com.ckfinder.demo.service.inter.IHistoryArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
public class HistoryArticleAPI {

    @Autowired
    private IHistoryArticleService historyArticleService;
//    @PostMapping("/create")
//    public ResponseEntity<HistoryArticleEntity> create(@RequestBody HisFavLaterRequest hisFavLaterRequest){
//        return new ResponseEntity<>(historyArticleService.insert(hisFavLaterRequest.getArticleId()), HttpStatus.OK);
//    }



}
