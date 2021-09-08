package com.finnal.blogit.controller.user;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.service.inter.INotificationService;
import com.finnal.blogit.service.inter.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/notification")
public class NotificationUserController {

    @Autowired
    private ITopicService topicService;

    @Autowired
    private INotificationService service;

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        Long accountId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        model.addAttribute("notifies", service.findAllByUserId(accountId));
        model.addAttribute("topics", topicService.findAll());
        return "user/notification";
    }
}
