package com.ckfinder.demo.controller;

import com.ckfinder.demo.entity.ArticleEntity;
import com.ckfinder.demo.service.inter.IArticleService;
import com.ckfinder.demo.service.inter.IHistoryArticleService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IHistoryArticleService historyArticleService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("article",new ArticleEntity());
        return "index";
    }

    @GetMapping("/article/findAll")
    public String findAll(Model model){
        model.addAttribute("articles",articleService.findAll());
        return "list-article";
    }

    @GetMapping("/article/{id}")
    public String oneArticle(@PathVariable("id") Long id, Model model){
        Optional<ArticleEntity> articleOptional = articleService.findById(id);
        if(!articleOptional.isPresent()){

        }
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        ArticleEntity article = articleOptional.get();
        if(userDetail != null){
            historyArticleService.insert(userDetail.getHistoryId(),article.getId());
        }
        articleService.plusCountView(article);
        model.addAttribute("article",articleService.findOne(id));
        return "one-article";
    }

    @GetMapping("/article/edit/{id}")
    public String editArticle(@PathVariable("id") Long id, Model model){
        model.addAttribute("article",articleService.findOne(id));
        return "index";
    }

    @GetMapping("/access-denied")
    public String denied(){
        return "redirect:/login";
    }


}
