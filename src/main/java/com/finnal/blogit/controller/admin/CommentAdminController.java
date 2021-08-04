package com.finnal.blogit.controller.admin;

import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

    @Autowired
    private ICommentService service;

    @Autowired
    private IArticleService aService;

    @GetMapping
    public String countComment(Model model){
        model.addAttribute("articles",service.listTotalComment());
        return "adminPage/comment" ;
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("postId") Long id, Model model) throws WebException{
        ArticleCustomDTO article = aService.getById(id).orElseThrow(WebException::new);
        model.addAttribute("title",article.getTitle());
        model.addAttribute("comments",service.getAllByArticleId(id));
        return "adminPage/detail-comment";
    }
}
