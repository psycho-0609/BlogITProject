package com.finnal.blogit.controller.admin;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.FavoriteArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.IFavoriteArticleService;
import com.finnal.blogit.service.inter.IReportService;
import com.finnal.blogit.service.inter.ITopicService;
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
@RequestMapping("/admin/post")
public class ArticleAdminController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IFavoriteArticleService favoriteArticleService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private ITopicService topicService;

    @GetMapping("/published")
    public String publishedPosts(Model model){
        model.addAttribute("articles",articleService.findByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC));
        model.addAttribute("type", Constant.PUBLISHED);
        return "/adminPage/article/published";
    }

    @GetMapping("/unapproved")
    public String unapproved(Model model){
        model.addAttribute("articles",articleService.findByPublishedAndStatus(ArticlePublished.DISABLE, ArticleStatus.PUBLIC));
        model.addAttribute("type", Constant.UNLISTED);
        return "/adminPage/article/unapproved";
    }

    @GetMapping("/allPosts")
    public String allPosts(Model model){
        model.addAttribute("articles",articleService.findByStatus(ArticleStatus.PUBLIC));
        model.addAttribute("type", Constant.ALL);
        return "/adminPage/article/allArticle";
    }

    @GetMapping("/{id}")
    public String detailPosts(@PathVariable("id") Long id,Model model) throws WebException{
        ArticleEntity article = articleService.findById(id).orElseThrow(WebException::new);
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(!article.getStatus().equals(ArticleStatus.PUBLIC)){
            throw new WebException();
        }
        if (userDetail != null) {
            FavoriteArticleEntity favoriteArticle = null;
            Optional<FavoriteArticleEntity> favOp = favoriteArticleService.findByFavIdAndArticleId(userDetail.getFavoriteId(), id);
            if (favOp.isPresent()) {
                favoriteArticle = favOp.get();
            }
            model.addAttribute("favStatus", favoriteArticle);
        }
        if(article.getNews().equals(ArticleNew.ENABLE)){
            article.setNews(ArticleNew.DISABLE);
            article = articleService.save(article);
        }
        model.addAttribute("article", article);
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("reports", reportService.findAll());
        return "/article/detailArticle";
    }
}
