package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.request.ReportRequest;
import com.finnal.blogit.dto.response.ArticleReportResponse;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.ReportEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
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
@RequestMapping("/api/report/")
public class ArticleReportAPI {

    @Autowired
    private IArticleReportService articleReportService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<ArticleReportResponse> create(@RequestBody ReportRequest request) throws APIException {
        validateData(request);
        ArticleEntity articleEntity = articleService.findById(request.getArticleId()).orElseThrow(()-> new ItemNotFoundException("Article not found"));
        ReportEntity reportEntity = reportService.findById(request.getTypeReportId()).orElseThrow(()-> new ItemNotFoundException("Type report not found"));
        ArticleReportEntity entity = new ArticleReportEntity();
        entity.setArticleEntity(articleEntity);
        entity.setReportEntity(reportEntity);
        entity.setContent(request.getComment());
        return new ResponseEntity<>(new ArticleReportResponse(articleReportService.save(entity).getId()),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ArticleReportResponse> delete(@PathVariable Long id) throws APIException{
        ArticleReportEntity entity = articleReportService.findById(id).orElseThrow(()-> new ItemNotFoundException("Article not found"));
        articleReportService.delete(entity.getId());
        return new ResponseEntity<>(new ArticleReportResponse(id),HttpStatus.OK);
    }

    private void validateData(ReportRequest request) throws APIException{
        if(request.getArticleId() == null){
            throw new ItemCannotEmptyException("Cannot report now");
        }
        if(request.getTypeReportId() == null){
            throw new ItemCannotEmptyException("Type report cannot empty");
        }
    }
}
