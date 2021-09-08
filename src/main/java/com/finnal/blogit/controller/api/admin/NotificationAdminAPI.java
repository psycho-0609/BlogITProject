package com.finnal.blogit.controller.api.admin;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.response.GetListNotification;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.entity.NotificationEntity;
import com.finnal.blogit.entity.enumtype.NotificationStatusType;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.INotificationService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin/notification")
public class NotificationAdminAPI {

    @Autowired
    private INotificationService service;

    @GetMapping("/getAll")
    public ResponseEntity<GetListNotification> getNotification(HttpSession session){
        UserInforAccountDTO accountDTO = (UserInforAccountDTO) session.getAttribute(Constant.USER);
        return new ResponseEntity<>(service.findAllByUserId(accountDTO.getId()), HttpStatus.OK);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<MessageDTO> updateStatus(@RequestParam("id") Long id, HttpSession session) throws APIException {
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        NotificationEntity entity = service.findById(id).orElseThrow(()-> new ItemNotFoundException("Notification is not found"));
        if(!entity.getAccount().getId().equals(userId)){
            throw new ItemCanNotModifyException("Can not edit");
        }
        service.updateStatus(entity);
        return new ResponseEntity<>(new MessageDTO("Success"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<MessageDTO> deleteAll(HttpSession session) throws APIException{
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if(service.countByAccountId(userId) <= 0){
            throw new ItemCanNotModifyException("Not any notification to delete");
        }
        service.deleteAllBUserId(userId);
        return new ResponseEntity<>(new MessageDTO("Success"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> deleteOne(@PathVariable("id") Long id,HttpSession session) throws APIException{
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        NotificationEntity entity = service.findById(id).orElseThrow(()-> new ItemNotFoundException("Notification is not found"));
        if(!entity.getAccount().getId().equals(userId)){
            throw new ItemCanNotModifyException("Can not delete this notification");
        }
        service.deleteById(entity.getId());
        return new ResponseEntity<>(new MessageDTO("Success"), HttpStatus.OK);
    }

    @PutMapping("/readAll")
    public ResponseEntity<MessageDTO> readAllById(HttpSession session) throws APIException{
        Long userId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if(service.countByAccountId(userId) <= 0){
            throw new ItemCanNotModifyException("Not any notification to mask as read all");
        }
        service.readedAll(userId);
        return new ResponseEntity<>(new MessageDTO("Success"), HttpStatus.OK);
    }

}
