package com.finnal.blogit.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ChatAdminController {

    @GetMapping("/chatBox")
    public String chatBox(){
        return "chatAdmin";
    }
}
