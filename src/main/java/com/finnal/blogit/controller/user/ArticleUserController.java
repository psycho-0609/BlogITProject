package com.finnal.blogit.controller.user;

import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/posts")
public class ArticleUserController {

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IArticleService articleService;


    @GetMapping("/newPost")
    public String addPosts(Model model){
        model.addAttribute("title","New post");
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("article",new ArticleEntity());
        return "user/add-post";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model) throws WebException {
        ArticleEntity entity = articleService.findById(id).orElseThrow(() -> new WebException());
        if (UserInfor.getPrincipal().getId().intValue() != entity.getUserAccount().getId().intValue()) {
            throw new WebException();
        }
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("article", entity);
        return "user/add-post";
    }
    @GetMapping("/public")
    public String publicPosts(@RequestParam(value = "title", required = false) String title, Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        if(title != null && !title.equals("")){
            model.addAttribute("articles",articleService.findAllForSearch(ArticlePublished.ENABLE,userDetail.getId(),ArticleStatus.PUBLIC, title));
        }else{
            model.addAttribute("articles",articleService.findAllByPublishedStatusAndAccount(ArticlePublished.ENABLE,userDetail.getId(),ArticleStatus.PUBLIC));
        }
        model.addAttribute("status",ArticleStatus.PUBLIC.getValue());
        model.addAttribute("title","Public Posts");
        model.addAttribute("topics",topicService.findAll());
        
        return "user/userArticle";
    }

    @GetMapping("/private")
    public String privatePosts(@RequestParam(value = "title", required = false) String title, Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        List<CustomArticleDTO> lists = articleService.findAllByAccountId(userDetail.getId());
        lists = lists.stream().filter(el -> el.getStatus().equals(ArticleStatus.PRIVATE.getValue())).collect(Collectors.toList());
        if(title != null && !title.equals("")){
            lists = lists.stream().filter(el -> el.getTitle().contains(title)).collect(Collectors.toList());
        }
        model.addAttribute("articles",lists);
        model.addAttribute("status",ArticleStatus.PRIVATE.getValue());
        model.addAttribute("title","Private Posts");
        model.addAttribute("topics",topicService.findAll());
        return "user/userArticle";
    }

    @GetMapping("/unapproved")
    public String unapprovedPosts(@RequestParam(value = "title", required = false) String title, Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        if(title != null && !title.equals("")){
            model.addAttribute("articles",articleService.findAllForSearch(ArticlePublished.DISABLE,userDetail.getId(),ArticleStatus.PUBLIC,title));
        }else{
            model.addAttribute("articles",articleService.findAllByPublishedStatusAndAccount(ArticlePublished.DISABLE,userDetail.getId(),ArticleStatus.PUBLIC));
        }
        model.addAttribute("status",3);
        model.addAttribute("title","Unapproved Posts");
        model.addAttribute("topics",topicService.findAll());
        return "user/userArticle";
    }


}
