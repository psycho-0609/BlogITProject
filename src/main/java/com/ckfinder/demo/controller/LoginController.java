package com.ckfinder.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/navbar")
    public String nav(){
        return "/common/navbar";
    }

    @GetMapping("/home")
    public String home(){
        return "/home";
    }

    @GetMapping("/articles")
    public String allAricle(){
        return "/article/allArticles";
    }
}
