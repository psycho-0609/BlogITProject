package com.finnal.blogit.controller.admin;

import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.dto.response.GetListArticleReport;
import com.finnal.blogit.dto.response.PaginationReport;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleReportService;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.untils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/report")
public class ArticleReportController {

    @Autowired
    private IArticleReportService service;

    @Autowired
    private IArticleService aService;

    @GetMapping
    public String allReport(Model model, @RequestParam("page") Integer page) throws WebException {
        if(page - 1 < 0){
            throw new WebException();
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Long> ids = aService.getListIdForReport(pageable);
        PaginationReport pagination = new PaginationReport();
        pagination.setReports(service.findAll(ids.getContent()));
        pagination.setTotalPage(Utility.getTotalPage(aService.countAllForReport()));
        model.addAttribute("pagination", pagination);
        return "adminPage/reportArticle";
    }

    @GetMapping("/all")
    public String allReportByArticle(@RequestParam("postId") Long id, Model model, @RequestParam("page") Integer page) throws WebException {
        if(id == null){
            throw new WebException();
        }
        if(page - 1 < 0){
            throw new WebException();
        }
        ArticleCustomDTO article = aService.getById(id).orElseThrow(WebException::new);
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids = service.getListIdForPagi(pageable, id);
        GetListArticleReport listArticleReport = new GetListArticleReport();
        listArticleReport.setArticle(article);
        listArticleReport.setReports(service.findAllReportByListId(ids.getContent()));
        listArticleReport.setTotalPage(Utility.getTotalPage(service.countAllByArticleId(id)));
        List<ArticleReportEntity> list = service.findAllByArticleIdAndNews(id);
        list.forEach(el -> el.setNews(ArticleReportNews.DISABLE));
        service.saveAll(list);
        model.addAttribute("articleReport",listArticleReport);
        return "adminPage/report-by-article";
    }

}
