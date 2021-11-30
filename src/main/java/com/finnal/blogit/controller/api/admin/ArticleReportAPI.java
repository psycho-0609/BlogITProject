package com.finnal.blogit.controller.api.admin;


import com.finnal.blogit.dto.response.*;

import com.finnal.blogit.entity.ArticleReportEntity;

import com.finnal.blogit.exception.api.APIException;

import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleReportService;

import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.untils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<PaginationReport> getAll(@RequestParam("page") Integer page) throws ItemCannotEmptyException {
        if(page - 1 < 0){
            throw new ItemCannotEmptyException("");
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Long> ids = aService.getListIdForReport(pageable);
        PaginationReport pagination = new PaginationReport();
        pagination.setReports(articleReportService.findAll(ids.getContent()));
        pagination.setTotalPage(Utility.getTotalPage(aService.countAllForReport()));
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessageDTO> deleteByArticle(@RequestParam("postId") Long id){
        articleReportService.deleteByArticleId(id);
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<GetListArticleReport> getAllByArticle(@RequestParam("postId") Long id, @RequestParam("page") Integer page) throws APIException{
        ArticleCustomDTO article = aService.getById(id).orElseThrow(() -> new ItemNotFoundException("Post is not found"));
        if(page - 1 < 0){
            throw new ItemCannotEmptyException("");
        }
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids = articleReportService.getListIdForPagi(pageable, id);
        GetListArticleReport listArticleReport = new GetListArticleReport();
        listArticleReport.setArticle(article);
        listArticleReport.setReports(articleReportService.findAllReportByListId(ids.getContent()));
        listArticleReport.setTotalPage(Utility.getTotalPage(articleReportService.countAllByArticleId(id)));
        return new ResponseEntity<>(listArticleReport, HttpStatus.OK);
    }
}
