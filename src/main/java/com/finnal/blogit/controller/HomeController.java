package com.finnal.blogit.controller;

import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.dto.response.PaginationArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.*;
import com.finnal.blogit.untils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private IBookMarkService rService;


    @GetMapping({"/home","/"})
    public String home(Model model){
        List<ArticleEntity> articleNews = articleService.findAllOrderNewsLimit();
        List<ArticleEntity> articlePopular = articleService.findAllOrderPopularLimit();
//        List<ArticleEntity> articleFav = articleService.getAllOderByFavCount(ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
//        if(articleFav.size() > 4){
//            articleFav = articleFav.stream().limit(4).collect(Collectors.toList());
//        }
        model.addAttribute("articleFav",articleService.findAllOrderRateLimit());
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("articlePrioritize",articleService.findByPrioritize());
        model.addAttribute("articleNews",articleNews);
        model.addAttribute("articlePopular",articlePopular);
        return "home";
    }
    @GetMapping("/search")
    public String Search(@RequestParam(value = "title") String title, @RequestParam("page") Integer page,  Model model) throws WebException {
        if(page - 1 < 0 ){
            throw new WebException();
        }
        Sort sort = Sort.by("publishedDate").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);
        Page<Long> items = articleService.getIdByTitleForPagi(pageable, title, ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        pagination.setArticles(articleService.getListArticleByListId(items.getContent()));
        Long totalArticle = articleService.countArticleByTitle(title, ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
        pagination.setTotalPage(Utility.getTotalPage(totalArticle));
        model.addAttribute("title","Search");
        model.addAttribute("paginationArticle", pagination);
        model.addAttribute("topics",topicService.findAll());
        return "article/allArticlesSearch";
    }

    @GetMapping("/author/{id}")
    public String articlesOfAuthor(@PathVariable("id") Long id, Model model, @RequestParam("page") Integer page) throws WebException {
        if(page - 1 < 0){
            throw new WebException();
        }
        UserAccountEntity entity = userAccount.findById(id).orElseThrow(WebException::new);
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page -1, 10, sort);
        Page<Long> ids = articleService.findListIdByPublishedAndStatusOfUser(pageable, "", ArticleStatus.PUBLIC, ArticlePublished.ENABLE, id);
        pagination.setArticles(articleService.getListArticleByListId(ids.getContent()));
        pagination.setTotalPage(Utility.getTotalPage(articleService.countByStatusAndPublishedAndUserId(ArticleStatus.PUBLIC, ArticlePublished.ENABLE, id, "")));
        List<CustomArticleDTO> listArticles = articleService.findAllByPublishedStatusAndAccount(ArticlePublished.ENABLE, entity.getId(),ArticleStatus.PUBLIC);
        List<Long> listIds = listArticles.stream().map(CustomArticleDTO::getId).collect(Collectors.toList());
        model.addAttribute("totalView",articleService.totalCountView(listIds));
        model.addAttribute("totalPost", listArticles.size());
        model.addAttribute("totalBookmark",rService.countTotalOfUser(id));
        model.addAttribute("author",entity);
        model.addAttribute("pagination",pagination);
        model.addAttribute("topics",topicService.findAll());
        model.addAttribute("title",entity.getUserDetailEntity().getFirstName() + " " + entity.getUserDetailEntity().getLastName());
        return "articlesOfAuthor";
    }


    @GetMapping("/chat")
    public String table(){
        return "chatMessage";
    }

    @GetMapping("/chatUser")
    public String chatUser(){
        return "chatAdmin";
    }


}
