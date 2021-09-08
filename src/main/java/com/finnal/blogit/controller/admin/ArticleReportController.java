package com.finnal.blogit.controller.admin;

import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.dto.response.GetListArticleReport;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleReportService;
import com.finnal.blogit.service.inter.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String allReport(Model model){
        model.addAttribute("reports",service.findAll());
        return "adminPage/reportArticle";
    }

    @GetMapping("/all")
    public String allReportByArticle(@RequestParam("postId") Long id, Model model) throws WebException {
        if(id == null){
            throw new WebException();
        }
        ArticleCustomDTO article = aService.getById(id).orElseThrow(WebException::new);
        GetListArticleReport listArticleReport = new GetListArticleReport();
        listArticleReport.setArticle(article);
        listArticleReport.setReports(service.findAllReportByArticleId(article.getId()));
        List<ArticleReportEntity> list = service.findAllByArticleIdAndNews(id);
        list.forEach(el -> el.setNews(ArticleReportNews.DISABLE));
        service.saveAll(list);
        model.addAttribute("articleReport",listArticleReport);
        return "adminPage/report-by-article";
    }

}
