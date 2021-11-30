package com.finnal.blogit.controller.api.admin;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.constant.MessageConstant;
import com.finnal.blogit.dto.request.ArticleChangeStatusRequest;
import com.finnal.blogit.dto.response.*;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.NotificationEntity;
import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.entity.enumtype.NotificationTypeType;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.exception.web.WebException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.IEmailService;
import com.finnal.blogit.service.inter.INotificationService;
import com.finnal.blogit.untils.Utility;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/post")
public class ArticleAdminAPI {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IEmailService emailService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleCustomDTO> getOne(@PathVariable("id") Long id) throws APIException {
        if(id == null){
            throw new ItemCannotEmptyException("ID cannot null");
        }

        return new ResponseEntity<>(articleService.getById(id).orElseThrow(()-> new ItemNotFoundException("Article is not found")), HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<MessageDTO> changeStatus(@RequestBody ArticleChangeStatusRequest request,
                                                   HttpServletRequest servletRequest)
            throws APIException, UnsupportedEncodingException, MessagingException, ParseException {
        if(request.getId() == null){
            throw new ItemCannotEmptyException("Id cannot null");
        }
        if(request.getPublished() == null){
            throw new ItemCannotEmptyException("Status cannot null");
        }
        ArticleEntity entity = articleService.findById(request.getId()).orElseThrow(()-> new ItemNotFoundException("Article is not found"));
        ArticlePublished lastPublished = entity.getPublished();
        if(ArticlePublished.ENABLE.equals(ArticlePublished.fromValue(request.getPublished()))){
            if(!request.getPrioritize().equals(0)){
                Optional<ArticleEntity> entityOptional = articleService.findByPrioritize(request.getPrioritize());
                // update Prioritize cho article co  Prioritize = request
                if(entityOptional.isPresent()){
                    if(!entityOptional.get().getId().equals(entity.getId())){
                        ArticleEntity entityStatus = entityOptional.get();
                        entityStatus.setPrioritize(0);
                        articleService.save(entityStatus);
                    }

                }
            }
            //setPublishedDate when article is disable
            if(entity.getPublished().equals(ArticlePublished.DISABLE)){
                entity.setPublishedDate(LocalDateTime.now());
            }
            entity.setPrioritize(request.getPrioritize());
        }
        entity.setPublished(ArticlePublished.fromValue(request.getPublished()));
        entity.setNews(ArticleNew.DISABLE);
        entity = articleService.save(entity);
        if(!lastPublished.equals(ArticlePublished.fromValue(request.getPublished()))){
            NotificationEntity notification = new NotificationEntity();
            if(ArticlePublished.ENABLE.equals(ArticlePublished.fromValue(request.getPublished()))){
                notification.setContent(MessageConstant.MESS_APPROVED_POST);
                notification.setUrl(MessageConstant.URL_DETAIL_POST_APPROVED + entity.getId());
                notification.setType(NotificationTypeType.APPROVED);
                emailService.approvedPost(entity, entity.getTopic().getName(), servletRequest);
            }else{
                notification.setContent(MessageConstant.MESS_DENIED_POST);
                notification.setUrl(MessageConstant.URL_DETAIL_POST_DENIED+ entity.getId());
                notification.setType(NotificationTypeType.DENINED);
                emailService.rejectPost(entity, entity.getTopic().getName(), servletRequest);
            }
            notification.setAccount(entity.getUserAccount());
            simpMessagingTemplate.convertAndSend(Constant.NOTIFICATION_URL_USER + entity.getUserAccount().getId(), notificationService.save(notification));
        }
        return new ResponseEntity<>(new MessageDTO("Your action is successfully"), HttpStatus.OK);
    }

    @GetMapping("/published")
    public ResponseEntity<PaginationArticleDTO> getArticlePublished(@RequestParam(value = "title", required = false) String title, @RequestParam("page") Integer page) throws ItemCannotEmptyException {
        if(page - 1 < 0){
            throw new ItemCannotEmptyException("");
        }
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = articleService.getIdByTitleForPagi(pageable, title, ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC, title)));
        }else{
            ids = articleService.getIdByTitleForPagi(pageable, "", ArticlePublished.ENABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.ENABLE, ArticleStatus.PUBLIC, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(ids.getContent()));
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    @GetMapping("/unlisted")
    public ResponseEntity<PaginationArticleDTO> getArticleUnlisted(@RequestParam(value = "title", required = false) String title, @RequestParam("page") Integer page) throws ItemCannotEmptyException {
        if(page - 1 < 0){
            throw new ItemCannotEmptyException("");
        }
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = articleService.getIdByTitleForPagi(pageable, title, ArticlePublished.DISABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.DISABLE, ArticleStatus.PUBLIC, title)));
        }else{
            ids = articleService.getIdByTitleForPagi(pageable, "", ArticlePublished.DISABLE, ArticleStatus.PUBLIC);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countArticleByPublishedAndStatus(ArticlePublished.DISABLE, ArticleStatus.PUBLIC, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(ids.getContent()));
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginationArticleDTO> getAllArticle(@RequestParam(value = "title", required = false) String title, @RequestParam ("page") Integer page) throws APIException{
        if(page - 1 < 0){
            throw new ItemCannotEmptyException("");
        }
        PaginationArticleDTO pagination = new PaginationArticleDTO();
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        Page<Long> ids;
        if(title != null){
            ids = articleService.getListIdByStatusAndTitleForPagi(pageable, ArticleStatus.PUBLIC, title);
            pagination.setTotalPage(Utility.getTotalPage(articleService.countAllByStatusAndTAndTitleLike(ArticleStatus.PUBLIC, title)));
        }else{
            ids = articleService.getListIdByStatusAndTitleForPagi(pageable, ArticleStatus.PUBLIC, "");
            pagination.setTotalPage(Utility.getTotalPage(articleService.countAllByStatusAndTAndTitleLike(ArticleStatus.PUBLIC, "")));
        }
        pagination.setArticles(articleService.getListArticleByListId(ids.getContent()));
        return new ResponseEntity<>(pagination, HttpStatus.OK);
    }

    private List<CustomArticleDTO> filterItem(List<CustomArticleDTO> list, String title){
        if(title != null && !Strings.isEmpty(title)){
            list = list.stream().filter(el -> el.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        }
        return list;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> deleteArticle(@PathVariable("id") Long id) throws APIException, IOException {
        ArticleEntity entity = articleService.findById(id).orElseThrow(()-> new ItemNotFoundException("Post is not found"));
        articleService.deleteArticle(entity.getId());
        return new ResponseEntity<>(new MessageDTO("Delete successfully"), HttpStatus.OK);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticCustomDTO>> getStatistic(){
        return new ResponseEntity<>(articleService.getForStatistic(), HttpStatus.OK);
    }


    @GetMapping("/statisticPie")
    public ResponseEntity<List<StatisticPieChartCustom>> getStatisticPie(){
        return new ResponseEntity<>(articleService.getStatisticPercent(), HttpStatus.OK);
    }

}
