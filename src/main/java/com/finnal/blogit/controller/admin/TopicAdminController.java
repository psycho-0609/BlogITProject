package com.finnal.blogit.controller.admin;

import com.finnal.blogit.service.inter.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
public class TopicAdminController {

    @Autowired
    private ITopicService tService;

    @GetMapping
    public String allTopic(Model model){
        model.addAttribute("topics",tService.findAll());
        return "/adminPage/topic";
    }
}
