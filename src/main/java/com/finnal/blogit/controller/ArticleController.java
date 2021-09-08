package com.finnal.blogit.controller;

import com.finnal.blogit.dto.response.GetListArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.FavoriteArticleEntity;
import com.finnal.blogit.entity.ReadLaterArticleEntity;
import com.finnal.blogit.entity.TopicEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.*;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/posts")
public class ArticleController {


    @Autowired
    private IArticleService articleService;

    @Autowired
    private IHistoryArticleService historyArticleService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IFavoriteArticleService favoriteArticleService;

    @Autowired
    private IReadLaterArticleService rlService;

    @Autowired
    private ICommentService commentService;

    @GetMapping
    public String findAll(Model model) {

        model.addAttribute("title","Posts");
        model.addAttribute("articles", articleService.findByPublishedAndStatusForWeb(ArticlePublished.ENABLE, ArticleStatus.PUBLIC));
        model.addAttribute("topics", topicService.findAll());
        return "article/allArticles";
    }

    @GetMapping("/topic/{topicId}")
    public String findByTopicId(@PathVariable("topicId") Integer id, Model model) throws WebException {
        TopicEntity entity = topicService.findById(id).orElseThrow(WebException::new);
        model.addAttribute("title",entity.getName());
        model.addAttribute("articles",articleService.findAllByTopicId(entity.getId()));
        model.addAttribute("topics", topicService.findAll());
        return "article/allArticles";
    }

    @GetMapping("/newest")
    public String newestPost(Model model){
        model.addAttribute("title","NEWEST");
        model.addAttribute("articles", articleService.getListNewestPost());
        model.addAttribute("topics", topicService.findAll());
        return "article/allArticles";
    }

    @GetMapping("/popular")
    public String popularPost(Model model){
        model.addAttribute("title","POPULAR");
        model.addAttribute("articles", articleService.getForPopular());
        model.addAttribute("topics", topicService.findAll());
        return "article/allArticles";
    }

    @GetMapping("/favorite")
    public String favoritePost(Model model){
        model.addAttribute("title","FAVORITE");
        model.addAttribute("articles", articleService.getListFavoritePost());
        model.addAttribute("topics", topicService.findAll());
        return "article/allArticles";
    }

    @GetMapping("/{id}")
    public String detailArticle(@PathVariable("id") Long id, Model model) throws WebException {
        ArticleEntity article = articleService.findById(id).orElseThrow(WebException::new);
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        List<ArticleEntity> list = articleService.findAllByTopicIdForRelease(article.getTopic().getId(), article.getId());
        if (article.getPublished().equals(ArticlePublished.ENABLE) && article.getStatus().equals(ArticleStatus.PUBLIC)) {
            articleService.plusCountView(article);
        }else{
            throw new WebException();
        }
        if (userDetail != null) {
            FavoriteArticleEntity favoriteArticle = null;
            historyArticleService.insert(userDetail.getId(), article.getId());
            Optional<FavoriteArticleEntity> favOp = favoriteArticleService.findByAccountIdAndArticleId(userDetail.getId(), id);
            if (favOp.isPresent()) {
                favoriteArticle = favOp.get();
            }
            model.addAttribute("favStatus", favoriteArticle);
            model.addAttribute("favStatus", favoriteArticle);
            Optional<ReadLaterArticleEntity> rlOp = rlService.findByAccountIdAndArticleId(userDetail.getId(), id);
            rlOp.ifPresent(readLaterArticleEntity -> model.addAttribute("bookMark", readLaterArticleEntity));
        }

        model.addAttribute("article", article);
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("reports", reportService.findAll());
        model.addAttribute("articlesRelease", list);
        model.addAttribute("comments",commentService.getAllByArticleId(id));
        return "article/detailArticle";
    }

    private Long  getTotalPage(Long totalArticle){
        if(totalArticle % 10 == 0){
            return totalArticle / 10;
        }else{
            return totalArticle / 10 + 1;
        }
    }

    @GetMapping("/access-denied")
    public String denied() {
        return "redirect:/login";
    }


}
