package com.finnal.blogit.controller.admin;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.response.PaginationArticleDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.*;
import com.finnal.blogit.untils.Utility;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    @Autowired
    private IBookMarkService rlService;

    @GetMapping("/published")
    public String publishedPosts(Model model, @RequestParam("page") Integer page, @RequestParam(value = "title", required = false) String title) throws WebException {
        if(page - 1 < 0){
            throw new WebException();
        }
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = articleService.getIdByTitleForPagi(pageable, title, ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC, title)));
        }else{
            ids = articleService.getIdByTitleForPagi(pageable, "", ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(ids.getContent()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("type", Constant.PUBLISHED);
        return "adminPage/article/published";
    }

    @GetMapping("/unapproved")
    public String unapproved(Model model, @RequestParam("page") Integer page, @RequestParam(value = "title", required = false) String title) throws WebException {
        if(page - 1 < 0){
            throw new WebException();
        }
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = articleService.getIdByTitleForPagi(pageable, title, ArticlePublished.DISABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.DISABLE, ArticleStatus.PUBLIC, title)));
        }else{
            ids = articleService.getIdByTitleForPagi(pageable, "", ArticlePublished.DISABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.DISABLE, ArticleStatus.PUBLIC, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(ids.getContent()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("type", Constant.UNLISTED);
        return "adminPage/article/unapproved";
    }

    @GetMapping("/allPosts")
    public String allPosts(Model model, @RequestParam("page") Integer page, @RequestParam(value = "title", required = false) String title) throws WebException {
        if(page - 1 < 0){
            throw new WebException();
        }
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = articleService.getListIdByStatusAndTitleForPagi(pageable, ArticleStatus.PUBLIC, title);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countAllByStatusAndTAndTitleLike(ArticleStatus.PUBLIC, title)));
        }else{
            ids = articleService.getListIdByStatusAndTitleForPagi(pageable, ArticleStatus.PUBLIC, "");
            pagination.setTotalPage(Utility.getTotalPage(articleService.countAllByStatusAndTAndTitleLike(ArticleStatus.PUBLIC, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(ids.getContent()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("type", Constant.ALL);
        return "adminPage/article/allArticle";
    }

    @GetMapping("/{id}")
    public String detailPosts(@PathVariable("id") Long id,Model model) throws WebException{
        ArticleEntity article = articleService.findById(id).orElseThrow(WebException::new);
        CustomUserDetail userDetail = UserInfor.getPrincipal();
//        if(!article.getStatus().equals(ArticleStatus.PUBLIC)){
//            throw new WebException();
//        }
//        if (userDetail != null) {
//            FavoriteArticleEntity favoriteArticle = null;
//            Optional<FavoriteArticleEntity> favOp = favoriteArticleService.findByAccountIdAndArticleId(userDetail.getId(), id);
//            if (favOp.isPresent()) {
//                favoriteArticle = favOp.get();
//            }
//            model.addAttribute("favStatus", favoriteArticle);
//            Optional<ReadLaterArticleEntity> rlOp = rlService.findByAccountIdAndArticleId(userDetail.getId(), id);
//            rlOp.ifPresent(readLaterArticleEntity -> model.addAttribute("bookMark", readLaterArticleEntity));
//        }
        if(article.getNews().equals(ArticleNew.ENABLE)){
            article.setNews(ArticleNew.DISABLE);
            article = articleService.save(article);
        }
        model.addAttribute("article", article);
        model.addAttribute("topics", topicService.findAll());
//        model.addAttribute("reports", reportService.findAll());
        return "adminPage/article/detailArticleAdmin";
    }
}
