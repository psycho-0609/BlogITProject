package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.dto.response.*;
import com.finnal.blogit.entity.BookMarkEntity;
import com.finnal.blogit.dto.request.HisFavLaterRequest;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.IBookMarkService;
import com.finnal.blogit.untils.Utility;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/readLater")
public class BookMarkArticleAPI {

    @Autowired
    private IBookMarkService readLaterArticleService;
    @Autowired
    private IArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<BookmarkResponse> create(@RequestBody HisFavLaterRequest requestData) throws APIException {
        CustomUserDetail customUserDetail = UserInfor.getPrincipal();
        if(!articleService.findById(requestData.getArticleId()).isPresent()){
            throw new ItemNotFoundException("Article not found");
        }
        BookMarkEntity entity = readLaterArticleService.create(customUserDetail.getId(), requestData.getArticleId());
        BookmarkResponse response = new BookmarkResponse(entity.getId(), 1, readLaterArticleService.countAllByArticleId(requestData.getArticleId()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookmarkResponse> delete(@PathVariable Long id) throws APIException{
        BookMarkEntity entity = readLaterArticleService.findById(id).orElseThrow(()-> new ItemNotFoundException("Not found read later"));
        if(!entity.getAccount().getId().equals(UserInfor.getPrincipal().getId())){
            throw new ItemCanNotModifyException("You cannot modify this read later");
        }
        readLaterArticleService.delete(entity.getId());
        return new ResponseEntity<>(new BookmarkResponse(entity.getId(),1, readLaterArticleService.countAllByArticleId(entity.getArticleEntity().getId())),HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<MessageDTO> deleteAll() throws APIException{
//        if(readLaterArticleService.countItemByReadLaterId(UserInfor.getPrincipal().getId()) <0){
//            throw new ItemNotFoundException("Not found any article in reader to delete");
//        }
        readLaterArticleService.deleteAll();
        return new ResponseEntity<>(new MessageDTO("Success"),HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PaginationBookmark> findByReadLater(@RequestParam(value = "title",required = false) String title, @RequestParam("page") Integer page) throws APIException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(page - 1 < 0){
            throw new ItemCannotEmptyException("Page must large than 0");
        }
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = readLaterArticleService.getListIdByUserId(pageable, userDetail.getId(), title);
        }else{
            ids = readLaterArticleService.getListIdByUserId(pageable, userDetail.getId(), "");
        }
        PaginationBookmark pagination = new PaginationBookmark();
        pagination.setTotalPage(Utility.getTotalPage(readLaterArticleService.countByUserId(userDetail.getId())));
        pagination.setBookmark(readLaterArticleService.findAllByAccountId(ids.getContent()));
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

}
