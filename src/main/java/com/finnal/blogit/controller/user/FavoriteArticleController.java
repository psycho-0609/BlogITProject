package com.finnal.blogit.controller.user;

import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IFavoriteArticleService;
import com.finnal.blogit.service.inter.ITopicService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/favorite")
public class FavoriteArticleController {

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IFavoriteArticleService favoriteArticleService;

    @GetMapping("/posts")
    public String getListFavArticleByFavId(@RequestParam(value = "title", required = false) String title, Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        if(title != null && !Strings.isEmpty(title)){
            model.addAttribute("favorites",favoriteArticleService.findForSearch(userDetail.getId(), title));
        }else{
            model.addAttribute("favorites",favoriteArticleService.getByFavId(userDetail.getId()));
        }
        model.addAttribute("topics",topicService.findAll());
        return "user/favArticles";
    }
}
