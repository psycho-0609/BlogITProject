package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.dto.response.FavResponseDTO;
import com.finnal.blogit.dto.response.GetFavArticle;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.entity.FavoriteArticleEntity;
import com.finnal.blogit.dto.request.HisFavLaterRequest;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCanNotModifyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IFavoriteArticleService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/favorite")
public class FavoriteArticleAPI {

    @Autowired
    private IFavoriteArticleService favoriteArticleService;

    @PostMapping("/create")
    public ResponseEntity<FavResponseDTO> create(@RequestBody HisFavLaterRequest requestData) throws APIException {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        Optional<FavoriteArticleEntity> entity = favoriteArticleService.findByAccountIdAndArticleId(userDetail.getId(),requestData.getArticleId());
        if(entity.isPresent()){
            throw new ItemNotFoundException("Fav da ton tai");
        }
        FavoriteArticleEntity favoriteSaved = favoriteArticleService.create(userDetail.getId(),requestData.getArticleId());
        Long countFav = favoriteArticleService.countByArticleId(requestData.getArticleId());
        return new ResponseEntity<>(new FavResponseDTO(favoriteSaved.getId(),1,countFav), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FavResponseDTO> delete(@PathVariable Long id) throws APIException {
        FavoriteArticleEntity favorite = favoriteArticleService.findById(id).orElseThrow(()-> new ItemNotFoundException("Fav not found"));

        if(UserInfor.getPrincipal().getId().intValue() != favorite.getAccount().getId().intValue()){
            throw new ItemCanNotModifyException("You can not modify");
        }
        favoriteArticleService.deleteById(id);
        return new ResponseEntity<>(new FavResponseDTO(id,2,favoriteArticleService.countByArticleId(favorite.getArticleEntity().getId())),HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<GetFavArticle>> getListFavByAccountId(@RequestParam(value = "title", required = false) String title) throws APIException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new ItemNotFoundException("User not found");
        }
        List<GetFavArticle> lists = favoriteArticleService.getByFavId(userDetail.getId());
        if(title != null && !title.equals("")){
            lists = lists.stream().filter(el -> el.getArticle().getTitle().contains(title)).collect(Collectors.toList());
        }
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<MessageDTO> deleteAll() throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        favoriteArticleService.deleteAllByFavId(userDetail.getId());
        return new ResponseEntity<>(new MessageDTO("Delete Successlly"), HttpStatus.OK);
    }
}
