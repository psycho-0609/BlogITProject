package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.entity.FavoriteArticleEntity;
import com.ckfinder.demo.request.HisFavLaterRequest;
import com.ckfinder.demo.service.inter.IFavoriteArticleService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteArticleAPI {

    @Autowired
    private IFavoriteArticleService favoriteArticleService;

    @PostMapping("/create")
    public String create(@RequestBody HisFavLaterRequest requestData){
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        Optional<FavoriteArticleEntity> entity = favoriteArticleService.findByFavIdAndArticleId(userDetail.getFavoriteId(),requestData.getArticleId());
        if(entity.isPresent()){
            return "da ton tai";
        }
        favoriteArticleService.create(userDetail.getFavoriteId(),requestData.getArticleId());
        return "create";
    }
}
