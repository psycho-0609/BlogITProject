package com.finnal.blogit.controller.admin;

import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/replyComment")
public class ReplyCommentAdminController {

    @Autowired
    private IReplyCommentService service;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") Long id) throws APIException {
        if(!service.isExistById(id)){
            throw new ItemNotFoundException("Comment is not found");
        }
        service.deleteById(id);
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }

}
