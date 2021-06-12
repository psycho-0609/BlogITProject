package com.finnal.blogit.controller.user;

import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ITopicService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/posts")
public class ArticleUserController {

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IArticleService articleService;

    @GetMapping("/public")
    public String publicPosts(Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        model.addAttribute("status",ArticleStatus.PUBLIC.getValue());
        model.addAttribute("title","Public Posts");
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("articles",articleService.findAllByStatusPublishedAndAccountId(ArticleStatus.PUBLIC, ArticlePublished.ENABLE,userDetail.getId()));
        return "/user/userArticle";
    }

    @GetMapping("/private")
    public String privatePosts(Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        model.addAttribute("status",ArticleStatus.PRIVATE.getValue());
        model.addAttribute("title","Private Posts");
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("articles",articleService.findAllByStatusAndUserAccount(ArticleStatus.PRIVATE, userDetail.getId()));
        return "/user/userArticle";
    }

    @GetMapping("/unapproved")
    public String unapprovedPosts(Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        model.addAttribute("status",3);
        model.addAttribute("title","Unapproved Posts");
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("articles",articleService.findAllByStatusPublishedAndAccountId(ArticleStatus.PUBLIC, ArticlePublished.DISABLE,userDetail.getId()));
        return "/user/userArticle";
    }
}
