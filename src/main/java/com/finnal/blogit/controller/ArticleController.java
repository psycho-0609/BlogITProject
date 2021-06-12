package com.finnal.blogit.controller;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.FavoriteArticleEntity;
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

    @GetMapping
    public String home(Model model) {
        model.addAttribute("article", new ArticleEntity());
        model.addAttribute("topics", topicService.findAll());   
        return "index";
    }

    @GetMapping("/findAll")
    public String findAll(Model model) {
        model.addAttribute("articles", articleService.findAll());
        model.addAttribute("topics", topicService.findAll());
        return "list-article";
    }

    @GetMapping("/all")
    public String allArticles(Model model) {
        model.addAttribute("articles", articleService.findAll());
        model.addAttribute("topics", topicService.findAll());
        return "/article/allArticles";
    }

    @GetMapping("/{id}")
    public String detailArticle(@PathVariable("id") Long id, Model model) throws WebException {
        ArticleEntity article = articleService.findById(id).orElseThrow(() -> new WebException());
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if (article.getPublished().equals(ArticlePublished.ENABLE) && article.getStatus().equals(ArticleStatus.PUBLIC)) {
            articleService.plusCountView(article);
        } else {
            if (userDetail != null) {
                if (!userDetail.getId().equals(article.getUserAccount().getId()) || !userDetail.getRoleType().equals(Constant.ADMIN_TYPE)) {
                    throw new WebException();
                }
            } else {
                throw new WebException();
            }

        }
        if (userDetail != null) {
            FavoriteArticleEntity favoriteArticle = null;
            historyArticleService.insert(userDetail.getHistoryId(), article.getId());
            Optional<FavoriteArticleEntity> favOp = favoriteArticleService.findByFavIdAndArticleId(userDetail.getFavoriteId(), id);
            if (favOp.isPresent()) {
                favoriteArticle = favOp.get();
            }
            model.addAttribute("favStatus", favoriteArticle);
        }

        model.addAttribute("article", article);
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("reports", reportService.findAll());
        return "article/detailArticle";
    }

    @GetMapping("/edit/{id}")
    public String editArticle(@PathVariable("id") Long id, Model model) throws WebException {
        ArticleEntity entity = articleService.findById(id).orElseThrow(() -> new WebException());
        if (UserInfor.getPrincipal().getId().intValue() != entity.getUserAccount().getId().intValue()) {
            throw new WebException();
        }
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("article", entity);
        return "index";
    }

    @GetMapping("/access-denied")
    public String denied() {
        return "redirect:/login";
    }


}
