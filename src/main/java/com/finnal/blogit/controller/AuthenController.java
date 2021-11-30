package com.finnal.blogit.controller;

import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthenController {

    @Autowired
    private IUserAccountService userAccountService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token, Model model) throws WebException {
        Optional<UserAccountEntity> entityOptional = userAccountService.findByToken(token);
        if(!entityOptional.isPresent()){
            throw new WebException();
        }

        model.addAttribute("user",entityOptional.get());
        return "reset-password";
    }


    @GetMapping("/confirmAccount")
    public String confirmAccount(@RequestParam("token") String token) throws WebException{
        UserAccountEntity entity = userAccountService.findByToken(token).orElseThrow(WebException::new);
        entity.setToken(null);
        entity.setStatus(AccountStatus.ENABLE);
        userAccountService.save(entity);
        return "confirm-account";
    }

    @GetMapping("/articles")
    public String allAricle(){
        return "article/allArticles";
    }
}
