package com.finnal.blogit.controller;

import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.ITopicService;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserAccountService accountService;

    @Autowired
    private ITopicService topicService;

    @GetMapping("/profile")
    public String personalInfor(Model model) throws WebException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        UserAccountEntity accountEntity = accountService.findById(userDetail.getId()).orElseThrow(()-> new WebException());
        model.addAttribute("user",accountEntity);
        model.addAttribute("title","Personal Information");
        model.addAttribute("topics",topicService.findAll());
        return "user/personalInfor";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model){
        model.addAttribute("topics",topicService.findAll());
        return "user/changePassword";
    }
}
