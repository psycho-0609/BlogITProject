package com.finnal.blogit.controller;

import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IUserAccountService userAccount;

    @Autowired
    private IFavoriteArticleService favoriteArticleService;


    @GetMapping({"/home","/"})
    public String home(Model model){
        List<CustomArticleDTO> articleNews = articleService.findByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
        if(articleNews.size() > 4){
            articleNews = articleNews.stream().limit(4L).collect(Collectors.toList());
        }
        List<ArticleEntity> articlePopular = articleService.getForPopular();
        if(articlePopular.size() > 4){
            articlePopular = articlePopular.stream().limit(4L).collect(Collectors.toList());
        }
        List<ArticleEntity> articleFav = articleService.getAllOderByFavCount(ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
        if(articleFav.size() > 4){
            articleFav = articleFav.stream().limit(4).collect(Collectors.toList());
        }
        model.addAttribute("articleFav",articleFav);
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("articlePrioritize",articleService.findByPrioritize());
        model.addAttribute("articleNews",articleNews);
        model.addAttribute("articlePopular",articlePopular);
        return "home";
    }
    @GetMapping("/search")
    public String Search(@RequestParam(value = "title", required = false) String title,  Model model){
        List<CustomArticleDTO>  list = articleService.findByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
        if(title != null && !Strings.isEmpty(title)){
            list = list.stream().filter(el -> el.getTitle().contains(title)).collect(Collectors.toList());
        }
        model.addAttribute("title","Search");
        model.addAttribute("articles",list);
        model.addAttribute("topics",topicService.findAll());
        return "article/allArticles";
    }

    @GetMapping("/author/{id}")
    public String articlesOfAuthor(@PathVariable("id") Long id, Model model) throws WebException {
        UserAccountEntity entity = userAccount.findById(id).orElseThrow(WebException::new);
        List<CustomArticleDTO> listArticles = articleService.findAllByPublishedStatusAndAccount(ArticlePublished.ENABLE, entity.getId(),ArticleStatus.PUBLIC);
        List<Long> listIds = listArticles.stream().map(CustomArticleDTO::getId).collect(Collectors.toList());
        model.addAttribute("totalView",articleService.totalCountView(listIds));
        model.addAttribute("totalFav",favoriteArticleService.countTotalFav(listIds));
        model.addAttribute("author",entity);
        model.addAttribute("articles",articleService.findAllByPublishedStatusAndAccount(ArticlePublished.ENABLE, entity.getId(),ArticleStatus.PUBLIC));
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("title",entity.getUserDetailEntity().getFirstName() + " " + entity.getUserDetailEntity().getLastName());
        return "articlesOfAuthor";
    }


    @GetMapping("/table")
    public String table(){
        return "adminPage/article/published";
    }

}
