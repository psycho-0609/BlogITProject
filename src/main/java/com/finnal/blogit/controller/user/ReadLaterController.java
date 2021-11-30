package com.finnal.blogit.controller.user;

import com.finnal.blogit.dto.response.PaginationBookmark;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.IBookMarkService;
import com.finnal.blogit.service.inter.ITopicService;
import com.finnal.blogit.untils.Utility;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/bookmark/")
public class ReadLaterController {

    @Autowired
    private IBookMarkService readLaterService;

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IArticleService articleService;

    @GetMapping("/posts")
    public String getAllByReadLaterId(@RequestParam(value = "title", required = false) String title, @RequestParam("page") Integer page, Model model) throws WebException{
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if(userDetail == null){
            throw new WebException();
        }
        if(page - 1 < 0){
            throw new WebException();
        }
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = readLaterService.getListIdByUserId(pageable, userDetail.getId(), title);
        }else{
            ids = readLaterService.getListIdByUserId(pageable, userDetail.getId(), "");
        }
        PaginationBookmark pagination = new PaginationBookmark();
        pagination.setTotalPage(Utility.getTotalPage(readLaterService.countByUserId(userDetail.getId())));
        pagination.setBookmark(readLaterService.findAllByAccountId(ids.getContent()));
        model.addAttribute("pagination", pagination);
        model.addAttribute("topics",topicService.findAll());
        return "user/readLater";
    }
}
