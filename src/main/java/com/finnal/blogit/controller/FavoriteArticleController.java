package com.finnal.blogit.controller;

import com.finnal.blogit.service.inter.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/favorite")
public class FavoriteArticleController {

    @Autowired
    private IFavoriteService favoriteService;

//    @GetMapping("/getAll")
//    public String getAll(){
//        FavoriteEntity favoriteEntity = new FavoriteEntity();
//        if(favoriteService.findByAccountId())
//    }
}
