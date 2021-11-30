package com.finnal.blogit.controller.admin;

import com.finnal.blogit.dto.response.PaginationAccount;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IUserAccountService;
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
@RequestMapping("/admin/account")
public class AccountAdminController {

    @Autowired
    private IUserAccountService accountService;

    @GetMapping
    public String getAll(Model model, @RequestParam(value = "email", required = false) String email, @RequestParam("page") Integer page) throws WebException {
        if(page - 1 < 0){
            throw new WebException();
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Long> ids;
        PaginationAccount pagination = new PaginationAccount();
        if(email != null){
            ids= accountService.getListIdForPagi(pageable, email);
            pagination.setTotalPage(Utility.getTotalPage(accountService.countAllAccount(email)));
        }else{
            ids= accountService.getListIdForPagi(pageable, "");
            pagination.setTotalPage(Utility.getTotalPage(accountService.countAllAccount("")));
        }
        pagination.setAccounts(accountService.getAllByListId(ids.getContent()));
        model.addAttribute("pagination",pagination);
        return "adminPage/account";
    }

}
