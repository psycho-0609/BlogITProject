package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.exception.api.APIException;
import com.ckfinder.demo.exception.api.ItemNotFoundException;
import com.ckfinder.demo.service.inter.IHistoryArticleService;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
public class HistoryArticleAPI {

    @Autowired
    private IHistoryArticleService historyArticleService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){

        historyArticleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll() throws APIException {
        if(historyArticleService.countAllByHistoryId(UserInfor.getPrincipal().getHistoryId()) <=0){
            throw new ItemNotFoundException("Not found any article in history to delete");
        }
        historyArticleService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }





}
