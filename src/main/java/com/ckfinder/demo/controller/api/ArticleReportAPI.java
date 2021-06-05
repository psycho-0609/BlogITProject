package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.dto.request.ArticleReportRequest;
import com.ckfinder.demo.dto.response.ArticleReportResponse;
import com.ckfinder.demo.entity.ArticleEntity;
import com.ckfinder.demo.entity.ArticleReportEntity;
import com.ckfinder.demo.entity.ReportEntity;
import com.ckfinder.demo.exception.api.APIException;
import com.ckfinder.demo.exception.api.ItemNotFoundException;
import com.ckfinder.demo.service.inter.IArticleReportService;
import com.ckfinder.demo.service.inter.IArticleService;
import com.ckfinder.demo.service.inter.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articlereport/")
public class ArticleReportAPI {

    @Autowired
    private IArticleReportService articleReportService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<ArticleReportResponse> create(@RequestBody ArticleReportRequest request) throws APIException {
        ArticleEntity articleEntity = articleService.findById(request.getArticleId()).orElseThrow(()-> new ItemNotFoundException("Article not found"));
        ReportEntity reportEntity = reportService.findById(request.getReportId()).orElseThrow(()-> new ItemNotFoundException("Type report not found"));
        ArticleReportEntity entity = new ArticleReportEntity();
        entity.setArticleEntity(articleEntity);
        entity.setReportEntity(reportEntity);
        entity.setContent(request.getContent());
        return new ResponseEntity<>(new ArticleReportResponse(articleReportService.save(entity).getId()),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ArticleReportResponse> delete(@PathVariable Long id) throws APIException{
        ArticleReportEntity entity = articleReportService.findById(id).orElseThrow(()-> new ItemNotFoundException("Not found"));
        articleReportService.delete(entity.getId());
        return new ResponseEntity<>(new ArticleReportResponse(id),HttpStatus.OK);
    }
}
