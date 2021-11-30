package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.response.GetHistoryFlowDate;
import com.finnal.blogit.dto.response.HistoryArticleDTO;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.entity.HistoryArticleEntity;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IHistoryArticleService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/user/history")
public class HistoryArticleAPI {

    @Autowired
    private IHistoryArticleService historyArticleService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> deleteById(@PathVariable Long id, HttpSession session) throws APIException{
        HistoryArticleEntity historyArticleEntity = historyArticleService.findById(id).orElseThrow(()->new ItemNotFoundException("Not found history"));
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if(!historyArticleEntity.getAccount().getId().equals(userId)){
            throw new ItemCanNotModifyException("Can not delete");
        }
        historyArticleService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Success"),HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<MessageDTO> deleteAll(HttpSession session) throws APIException {
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if(historyArticleService.countAllByAccountId(userId) <= 0){
            throw new ItemNotFoundException("Not found any article in history to delete");
        }
        historyArticleService.deleteAll();
        return new ResponseEntity<>(new MessageDTO("Success"),HttpStatus.OK);
    }

//    @GetMapping("/posts")
//    public ResponseEntity<List<HistoryArticleDTO>> findAllByHistoryId() throws APIException{
//        CustomUserDetail userDetail = UserInfor.getPrincipal();
//        if(userDetail == null){
//            throw new ItemNotFoundException("Not found user");
//        }
//
//        return new ResponseEntity<>(historyArticleService.findAllByHistoryId(userDetail.getHistoryId()), HttpStatus.OK);
//    }

    @GetMapping("/posts")
    public ResponseEntity<List<GetHistoryFlowDate>> getAllByFlowDate(@RequestParam(value = "title", required = false) String title, HttpSession session) throws APIException{
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if(title != null && !title.equals("")){
            return new ResponseEntity<>(historyArticleService.getAllForSearch(userId, title), HttpStatus.OK);
        }
        return new ResponseEntity<>(historyArticleService.getHistoryArticleFlowDate(userId), HttpStatus.OK);
    }





}
