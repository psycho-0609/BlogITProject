package com.ckfinder.demo.controller.api;

import com.ckfinder.demo.dto.response.FavReadResponse;
import com.ckfinder.demo.entity.FavoriteArticleEntity;
import com.ckfinder.demo.dto.request.HisFavLaterRequest;
import com.ckfinder.demo.exception.api.APIException;
import com.ckfinder.demo.exception.api.ItemCanNotModifyException;
import com.ckfinder.demo.exception.api.ItemNotFoundException;
import com.ckfinder.demo.service.inter.IFavoriteArticleService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteArticleAPI {

    @Autowired
    private IFavoriteArticleService favoriteArticleService;

    @PostMapping("/create")
    public ResponseEntity<FavReadResponse> create(@RequestBody HisFavLaterRequest requestData) throws APIException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        Optional<FavoriteArticleEntity> entity = favoriteArticleService.findByFavIdAndArticleId(userDetail.getFavoriteId(),requestData.getArticleId());
        if(entity.isPresent()){
            throw new ItemNotFoundException("Fav da ton tai");
        }
        return new ResponseEntity<>(new FavReadResponse(favoriteArticleService.create(userDetail.getFavoriteId(),requestData.getArticleId()).getId()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws APIException {
        Optional<FavoriteArticleEntity> favoriteOp = favoriteArticleService.findById(id);
        if(!favoriteOp.isPresent()){
            throw new ItemNotFoundException("Fav not found");
        }
        if(UserInfor.getPrincipal().getFavoriteId().intValue() != favoriteOp.get().getFavoriteEntity().getId().intValue()){
            throw new ItemCanNotModifyException("You can not modify");
        }
        favoriteArticleService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
