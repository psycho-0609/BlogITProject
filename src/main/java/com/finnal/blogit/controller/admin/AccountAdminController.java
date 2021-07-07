package com.finnal.blogit.controller.admin;

import com.finnal.blogit.service.inter.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/account")
public class AccountAdminController {

    @Autowired
    private IUserAccountService accountService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("accounts",accountService.getAllAccountUser());
        return "/adminPage/account";
    }

}
