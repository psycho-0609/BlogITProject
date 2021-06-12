package com.finnal.blogit.controller.api;

import com.finnal.blogit.dto.response.FavResponseDTO;
import com.finnal.blogit.entity.FavoriteArticleEntity;
import com.finnal.blogit.dto.request.HisFavLaterRequest;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IFavoriteArticleService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
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
    public ResponseEntity<FavResponseDTO> create(@RequestBody HisFavLaterRequest requestData) throws APIException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        Optional<FavoriteArticleEntity> entity = favoriteArticleService.findByFavIdAndArticleId(userDetail.getFavoriteId(),requestData.getArticleId());
        if(entity.isPresent()){
            throw new ItemNotFoundException("Fav da ton tai");
        }
        FavoriteArticleEntity favoriteSaved = favoriteArticleService.create(userDetail.getFavoriteId(),requestData.getArticleId());
        Long countFav = favoriteArticleService.countByArticleId(requestData.getArticleId());
        return new ResponseEntity<>(new FavResponseDTO(favoriteSaved.getId(),1,countFav), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FavResponseDTO> delete(@PathVariable Long id) throws APIException {
        FavoriteArticleEntity favorite = favoriteArticleService.findById(id).orElseThrow(()-> new ItemNotFoundException("Fav not found"));

        if(UserInfor.getPrincipal().getFavoriteId().intValue() != favorite.getFavoriteEntity().getId().intValue()){
            throw new ItemCanNotModifyException("You can not modify");
        }
        favoriteArticleService.deleteById(id);
        return new ResponseEntity<>(new FavResponseDTO(id,2,favoriteArticleService.countByArticleId(favorite.getArticleEntity().getId())),HttpStatus.OK);
    }
}
