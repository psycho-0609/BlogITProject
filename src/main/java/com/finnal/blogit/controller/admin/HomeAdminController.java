package com.finnal.blogit.controller.admin;

import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ITopicService;
import com.finnal.blogit.service.inter.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAdminController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IUserAccountService accountService;

    @Autowired
    private ITopicService topicService;

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("totalArticle", articleService.countAllByStatusAndPublished());
        model.addAttribute("totalTopic",topicService.countAllTopic());
        model.addAttribute("totalAccount", accountService.accountAllAccountByStatus());
        return "/adminPage/homeAdmin";
    }
}
