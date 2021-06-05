package com.ckfinder.demo.controller;

import com.ckfinder.demo.entity.FavoriteEntity;
import com.ckfinder.demo.service.inter.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
