package com.ckfinder.demo.controller;

import com.ckfinder.demo.config.CKFinderServletConfig;
import com.ckfinder.demo.entity.ArticleEntity;
import com.ckfinder.demo.exception.web.WebException;
import com.ckfinder.demo.service.inter.IArticleService;
import com.ckfinder.demo.service.inter.IHistoryArticleService;
import com.ckfinder.demo.service.inter.ITopicService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ArticleController {


    @Autowired
    private IArticleService articleService;

    @Autowired
    private IHistoryArticleService historyArticleService;

    @Autowired
    private ITopicService topicService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("article",new ArticleEntity());
        model.addAttribute("topics",topicService.findAll());
        return "index";
    }

    @GetMapping("/article/findAll")
    public String findAll(Model model){
        model.addAttribute("articles",articleService.findAll());
        return "list-article";
    }

    @GetMapping("/article/all")
    public String allArticles(Model model){
        model.addAttribute("articles",articleService.findAll());
        return "/article/allArticles";
    }

    @GetMapping("/article/{id}")
    public String detailArticle(@PathVariable("id") Long id, Model model) throws WebException{
        ArticleEntity article = articleService.findById(id).orElseThrow(()-> new WebException());
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail != null){
            historyArticleService.insert(userDetail.getHistoryId(),article.getId());
        }
        articleService.plusCountView(article);
        model.addAttribute("article",article);
        return "article/detailArticle";
    }

    @GetMapping("/article/edit/{id}")
    public String editArticle(@PathVariable("id") Long id, Model model) throws WebException{
        ArticleEntity entity = articleService.findById(id).orElseThrow(() -> new WebException());
        if(UserInfor.getPrincipal().getId().intValue() != entity.getUserAccount().getId().intValue()){
            throw new WebException();
        }
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("article",entity);
        return "index";
    }

    @GetMapping("/access-denied")
    public String denied(){
        return "redirect:/login";
    }


}
