package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.request.ArticleReportRequest;
import com.finnal.blogit.dto.response.ArticleReportResponse;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.ReportEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IArticleReportService;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.IReportService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/report/")
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
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        ArticleReportEntity entity = articleReportService.findById(id).orElseThrow(()-> new ItemNotFoundException("Article not found"));
        articleReportService.delete(entity.getId());
        return new ResponseEntity<>(new ArticleReportResponse(id),HttpStatus.OK);
    }
}
