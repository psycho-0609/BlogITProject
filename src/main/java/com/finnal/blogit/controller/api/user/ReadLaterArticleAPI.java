package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.dto.response.BookmarkResponse;
import com.finnal.blogit.dto.response.FavReadResponse;
import com.finnal.blogit.dto.response.GetListReadLater;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.entity.ReadLaterArticleEntity;
import com.finnal.blogit.dto.request.HisFavLaterRequest;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.IReadLaterArticleService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/readLater")
public class ReadLaterArticleAPI {

    @Autowired
    private IReadLaterArticleService readLaterArticleService;
    @Autowired
    private IArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<BookmarkResponse> create(@RequestBody HisFavLaterRequest requestData) throws APIException {
        CustomUserDetail customUserDetail = UserInfor.getPrincipal();
        if(!articleService.findById(requestData.getArticleId()).isPresent()){
            throw new ItemNotFoundException("Article not found");
        }
        ReadLaterArticleEntity entity = readLaterArticleService.create(customUserDetail.getId(), requestData.getArticleId());
        BookmarkResponse response = new BookmarkResponse(entity.getId(), 1, readLaterArticleService.countAllByArticleId(requestData.getArticleId()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookmarkResponse> delete(@PathVariable Long id) throws APIException{
        ReadLaterArticleEntity entity = readLaterArticleService.findById(id).orElseThrow(()-> new ItemNotFoundException("Not found read later"));
        if(!entity.getAccount().getId().equals(UserInfor.getPrincipal().getId())){
            throw new ItemCanNotModifyException("You cannot modify this read later");
        }
        readLaterArticleService.delete(entity.getId());
        return new ResponseEntity<>(new BookmarkResponse(entity.getId(),1, readLaterArticleService.countAllByArticleId(entity.getArticleEntity().getId())),HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<MessageDTO> deleteAll() throws APIException{
        if(readLaterArticleService.countItemByReadLaterId(UserInfor.getPrincipal().getId()) <0){
            throw new ItemNotFoundException("Not found any article in reader to delete");
        }
        readLaterArticleService.deleteAll();
        return new ResponseEntity<>(new MessageDTO("Success"),HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<GetListReadLater>> findByReadLater(@RequestParam(value = "title",required = false) String title) throws APIException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new ItemNotFoundException("Not found user");
        }
        List<GetListReadLater> lists = readLaterArticleService.findAllByAccountId(userDetail.getId());
        if(title != null && !title.equals("")){
            lists = lists.stream().filter(el -> el.getArticle().getTitle().contains(title)).collect(Collectors.toList());
        }
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

}
