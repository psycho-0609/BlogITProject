package com.finnal.blogit.controller.api.admin;

import com.finnal.blogit.dto.response.CommentDTO;
import com.finnal.blogit.dto.response.ListArticleComment;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.PaginationComment;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.ICommentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/comment")
public class CommentAdminAPI {

    @Autowired
    private ICommentService service;

    @Autowired
    private IArticleService aService;

    @GetMapping("/getAll")
    public ResponseEntity<PaginationComment> getAll(@RequestParam(value = "title", required = false) String title, @RequestParam("page") Integer page) throws APIException {
        if(page - 1 < 0){
            throw new ItemNotFoundException("");
        }
        Sort sort = Sort.by("createdDate");
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        PaginationComment pagination = new PaginationComment();
        if(title != null){
            ids = service.getListArticleId(pageable, title);
            pagination.setTotalPage(service.countListArticle(title));
        }else{
            ids = service.getListArticleId(pageable, "");
            pagination.setTotalPage(service.countListArticle(""));
        }
        pagination.setList(service.listTotalComment(ids.getContent()));
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<List<CommentDTO>> detail(@RequestParam("postId") Long id) throws APIException {
        if(!aService.isExistedById(id)){
            throw new ItemNotFoundException("Post is not found");
        }
        return new ResponseEntity<>(service.getAllByArticleId(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable("id") Long id) throws APIException {
        if(!service.existsById(id)){
            throw new ItemNotFoundException("Comment is not found");
        }
        service.deleteById(id);
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }
}
