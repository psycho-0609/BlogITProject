package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IHistoryArticleService;
import com.finnal.blogit.user.UserInfor;
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
    public ResponseEntity<MessageDTO> deleteById(@PathVariable Long id){

        historyArticleService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Success"),HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<MessageDTO> deleteAll() throws APIException {
        if(historyArticleService.countAllByHistoryId(UserInfor.getPrincipal().getHistoryId()) <=0){
            throw new ItemNotFoundException("Not found any article in history to delete");
        }
        historyArticleService.deleteAll();
        return new ResponseEntity<>(new MessageDTO("Success"),HttpStatus.OK);
    }





}
