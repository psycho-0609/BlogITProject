package com.finnal.blogit.controller;

import com.finnal.blogit.service.inter.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ITopicService topicService;
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("topics",topicService.findAll());
        return "/home";
    }
    @GetMapping("/user")
    public String user(Model model){
        model.addAttribute("topics",topicService.findAll());
        return "/user/userArticle";
    }

    @GetMapping("/user/infor")
    public String Infor(Model model){
        model.addAttribute("topics",topicService.findAll());
        return "/user/personalInfor";
    }
}
