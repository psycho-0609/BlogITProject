package com.finnal.blogit.controller.api.admin;


import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.dto.response.GetListArticleReport;
import com.finnal.blogit.dto.response.GetListReport;

import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.entity.ArticleReportEntity;

import com.finnal.blogit.exception.api.APIException;

import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleReportService;

import com.finnal.blogit.service.inter.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/report")
public class ArticleReportAPI {

    @Autowired
    private IArticleReportService articleReportService;

    @Autowired
    private IArticleService aService;


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable Long id) throws APIException{
        ArticleReportEntity entity = articleReportService.findById(id).orElseThrow(()-> new ItemNotFoundException("Report not found"));
        articleReportService.delete(entity.getId());
        return new ResponseEntity<>(new MessageDTO("Delete successfully"),HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<GetListReport>> getAll(){
        List<GetListReport> list = articleReportService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessageDTO> deleteByArticle(@RequestParam("postId") Long id){
        articleReportService.deleteByArticleId(id);
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<GetListArticleReport> getAllByArticle(@RequestParam("postId") Long id) throws APIException{
        if(id == null){
            throw new ItemCannotEmptyException("Post Id cannot empty");
        }
        ArticleCustomDTO article = aService.getById(id).orElseThrow(() -> new ItemNotFoundException("Post is not found"));
        GetListArticleReport listArticleReport = new GetListArticleReport();
        listArticleReport.setArticle(article);
        listArticleReport.setReports(articleReportService.findAllReportByArticleId(article.getId()));
        return new ResponseEntity<>(listArticleReport, HttpStatus.OK);
    }
}
