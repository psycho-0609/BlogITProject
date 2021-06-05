package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.entity.ReadLaterArticleEntity;
import com.ckfinder.demo.dto.request.HisFavLaterRequest;
import com.ckfinder.demo.exception.api.APIException;
import com.ckfinder.demo.exception.api.ItemCanNotModifyException;
import com.ckfinder.demo.exception.api.ItemExitedException;
import com.ckfinder.demo.exception.api.ItemNotFoundException;
import com.ckfinder.demo.service.inter.IReadLaterArticleService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/readLater")
public class ReadLaterArticleAPI {

    @Autowired
    private IReadLaterArticleService readLaterArticleService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody HisFavLaterRequest requestData) throws APIException {
        CustomUserDetail customUserDetail = UserInfor.getPrincipal();
        Optional<ReadLaterArticleEntity> readLaterArticleOp = readLaterArticleService.findByReadLaterIdAndArticleId(customUserDetail.getReadLaterId(), requestData.getArticleId());
        if(readLaterArticleOp.isPresent()){
            throw new ItemExitedException("Read later is existed");
        }
        readLaterArticleService.create(customUserDetail.getReadLaterId(), requestData.getArticleId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws APIException{
        ReadLaterArticleEntity entity = readLaterArticleService.findById(id).orElseThrow(()-> new ItemNotFoundException("Not found read later"));
        if(!entity.getReadLaterEntity().getId().equals(UserInfor.getPrincipal().getReadLaterId())){
            throw new ItemCanNotModifyException("You cannot modify this read later");
        }
        readLaterArticleService.delete(entity.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll() throws APIException{
        if(readLaterArticleService.countItemByReadLaterId(UserInfor.getPrincipal().getReadLaterId()) <0){
            throw new ItemNotFoundException("Not found any article in reader to delete");
        }
        readLaterArticleService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

}
