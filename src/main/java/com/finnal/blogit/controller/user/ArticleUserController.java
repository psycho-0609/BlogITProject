package com.finnal.blogit.controller.user;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.dto.response.PaginationArticleDTO;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ITopicService;
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

import javax.servlet.http.HttpSession;
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
    public String addPosts(Model model) {
        model.addAttribute("title", "New post");
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("article", new ArticleEntity());
        return "user/add-post";
    }

    @GetMapping("/{id}")
    public String detailPost(@PathVariable("id") Long id, Model model) throws WebException {
        ArticleEntity article = articleService.findById(id).orElseThrow(WebException::new);
        if (!UserInfor.getPrincipal().getId().equals(article.getUserAccount().getId())) {
            throw new WebException();
        }
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("article", article);
        return "article/detailArticle";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model, HttpSession session) throws WebException {
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        ArticleEntity entity = articleService.findById(id).orElseThrow(WebException::new);
        if (!entity.getUserAccount().getId().equals(userId)) {
            throw new WebException();
        }
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("article", entity);
        return "user/add-post";
    }

    @GetMapping("/public")
    public String publicPosts(@RequestParam(value = "title", required = false) String title, @RequestParam("page") Integer page, Model model, HttpSession session) throws WebException {
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if (page - 1 < 0) {
            throw new WebException();
        }
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> listId;
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        if (title != null) {
            listId = articleService.findListIdByPublishedAndStatusOfUser(pageable, title, ArticleStatus.PUBLIC, ArticlePublished.ENABLE, userId);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countByStatusAndPublishedAndUserId(ArticleStatus.PUBLIC, ArticlePublished.ENABLE, userId, title)));
        } else {
            listId = articleService.findListIdByPublishedAndStatusOfUser(pageable, "", ArticleStatus.PUBLIC, ArticlePublished.ENABLE, userId);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countByStatusAndPublishedAndUserId(ArticleStatus.PUBLIC, ArticlePublished.ENABLE, userId, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(listId.getContent()));
        model.addAttribute("status", ArticleStatus.PUBLIC.getValue());
        model.addAttribute("title", "Public Posts");
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("pagination", pagination);
        return "user/userArticle";
    }

    @GetMapping("/private")
    public String privatePosts(@RequestParam(value = "title", required = false) String title, @RequestParam("page") Integer page, Model model, HttpSession session) throws WebException {
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if (page - 1 < 0) {
            throw new WebException();
        }
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> listId;
        if (title != null) {
            listId = articleService.getListIdPrivate(pageable, title, userId);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countByStatusAndUserId(ArticleStatus.PRIVATE, userId, title)));
        } else {
            listId = articleService.getListIdPrivate(pageable, "", userId);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countByStatusAndUserId(ArticleStatus.PRIVATE, userId, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(listId.getContent()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("status", ArticleStatus.PRIVATE.getValue());
        model.addAttribute("title", "Private Posts");
        model.addAttribute("topics", topicService.findAll());
        return "user/userArticle";
    }

    @GetMapping("/unapproved")
    public String unapprovedPosts(@RequestParam(value = "title", required = false) String title, @RequestParam("page") Integer page, Model model, HttpSession session) throws WebException {
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if (page - 1 < 0) {
            throw new WebException();
        }
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> listId;
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        if (title != null) {
            listId = articleService.findListIdByPublishedAndStatusOfUser(pageable, title, ArticleStatus.PUBLIC, ArticlePublished.DISABLE, userId);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countByStatusAndPublishedAndUserId(ArticleStatus.PUBLIC, ArticlePublished.DISABLE, userId, title)));
        } else {
            listId = articleService.findListIdByPublishedAndStatusOfUser(pageable, "", ArticleStatus.PUBLIC, ArticlePublished.DISABLE, userId);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countByStatusAndPublishedAndUserId(ArticleStatus.PUBLIC, ArticlePublished.DISABLE, userId, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(listId.getContent()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("status", 3);
        model.addAttribute("title", "Unapproved Posts");
        model.addAttribute("topics", topicService.findAll());
        return "user/userArticle";
    }



}
