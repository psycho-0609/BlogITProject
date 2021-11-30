package com.finnal.blogit.controller.admin;

import com.finnal.blogit.dto.response.ArticleCustomDTO;
import com.finnal.blogit.dto.response.PaginationComment;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ICommentService;
import com.finnal.blogit.untils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public String countComment(Model model, @RequestParam("page") Integer page, @RequestParam(value = "title", required = false) String title) throws WebException {
        if(page - 1 < 0){
            throw new WebException();
        }
        Sort sort = Sort.by("createdDate");
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        PaginationComment pagination = new PaginationComment();
        if(title != null){
            ids = service.getListArticleId(pageable, title);
            pagination.setTotalPage(Utility.getTotalPage(service.countListArticle(title)));
        }else{
            ids = service.getListArticleId(pageable, "");
            pagination.setTotalPage(Utility.getTotalPage(service.countListArticle("")));
        }
        pagination.setList(service.listTotalComment(ids.getContent()));
        model.addAttribute("pagination",pagination);
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
