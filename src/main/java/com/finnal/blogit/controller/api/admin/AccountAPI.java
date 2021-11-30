package com.finnal.blogit.controller.api.admin;

import com.finnal.blogit.dto.request.ChangeStatusAccountRequest;
import com.finnal.blogit.dto.response.CustomUserAccount;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.PaginationAccount;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.imp.UserAccountService;
import com.finnal.blogit.untils.Utility;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/account")
public class AccountAPI {

    @Autowired
    private UserAccountService service;

    @GetMapping("/getAll")
    private ResponseEntity<PaginationAccount> getAll(@RequestParam(value = "email", required = false) String email, @RequestParam("page") Integer page) throws ItemCannotEmptyException {
        if(page - 1 < 0){
            throw new ItemCannotEmptyException("");
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Long> ids;
        PaginationAccount pagination = new PaginationAccount();
        if(email != null){
            ids= service.getListIdForPagi(pageable, email);
            pagination.setTotalPage(Utility.getTotalPage(service.countAllAccount(email)));
        }else{
            ids= service.getListIdForPagi(pageable, "");
            pagination.setTotalPage(Utility.getTotalPage(service.countAllAccount("")));
        }
        pagination.setAccounts(service.getAllByListId(ids.getContent()));
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomUserAccount> getOne(@PathVariable("id") Long id) throws APIException {
        if(id == null){
            throw new ItemCannotEmptyException("Id is required");
        }
        CustomUserAccount account = service.findOneById(id).orElseThrow(()-> new ItemNotFoundException("Account is not found"));
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<MessageDTO> changeStatus(@RequestBody ChangeStatusAccountRequest request) throws APIException{
        if(request.getId() == null){
            throw new ItemCannotEmptyException("ID is required");
        }
        if(!request.getStatus().equals(AccountStatus.ENABLE.getValue()) && !request.getStatus().equals(AccountStatus.DISABLE.getValue())){
            throw new ItemCannotEmptyException("Status not valid");
        }
        UserAccountEntity account = service.findById(request.getId()).orElseThrow(()-> new ItemNotFoundException("Account is not found"));
        account.setStatus(AccountStatus.fromValue(request.getStatus()));
        service.save(account);
        return new ResponseEntity<>(new MessageDTO("Your action is successfully"), HttpStatus.OK);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<UserInforDto> findOneById(@PathVariable("id") Long id) throws APIException{
        UserInforDto user = service.findUserById(id).orElseThrow(()-> new ItemNotFoundException("User is not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
