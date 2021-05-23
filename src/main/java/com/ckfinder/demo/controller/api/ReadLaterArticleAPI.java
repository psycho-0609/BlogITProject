package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.entity.ReadLaterArticleEntity;
import com.ckfinder.demo.request.HisFavLaterRequest;
import com.ckfinder.demo.service.inter.IReadLaterArticleService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/readLater")
public class ReadLaterArticleAPI {

    @Autowired
    private IReadLaterArticleService readLaterArticleService;

    @PostMapping("/create")
    public String create(@RequestBody HisFavLaterRequest requestData) {
        CustomUserDetail customUserDetail = UserInfor.getPrincipal();
        Optional<ReadLaterArticleEntity> readLaterArticleOp = readLaterArticleService.findByReadLaterIdAndArticleId(customUserDetail.getReadLaterId(), requestData.getArticleId());
        if(readLaterArticleOp.isPresent()){
            return "da ton tai";
        }
        readLaterArticleService.create(customUserDetail.getReadLaterId(), requestData.getArticleId());
        return "creat";
    }

}
