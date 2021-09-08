package com.finnal.blogit.controller.api.web;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.constant.MessageConstant;
import com.finnal.blogit.dto.request.ReportRequest;
import com.finnal.blogit.dto.response.ArticleReportResponse;
import com.finnal.blogit.dto.response.UserInforDto;
import com.finnal.blogit.entity.*;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import com.finnal.blogit.entity.enumtype.NotificationTypeType;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/user/report")
public class ArticleReportWebAPI {
    @Autowired
    private IArticleReportService articleReportService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IReportService reportService;

    @Autowired
    private IUserAccountService accountService;

    @Autowired
    private INotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<ArticleReportResponse> create(@RequestBody ReportRequest request, HttpSession session) throws APIException {
        Long accountId = ((UserInforDto) session.getAttribute(Constant.USER)).getId();
        validateData(request);
        ArticleEntity articleEntity = articleService.findById(request.getArticleId()).orElseThrow(()-> new ItemNotFoundException("Article not found"));
        ReportEntity reportEntity = reportService.findById(request.getTypeReportId()).orElseThrow(()-> new ItemNotFoundException("Type report not found"));
        ArticleReportEntity entity = new ArticleReportEntity();
        entity.setUserAccount(new UserAccountEntity(accountId));
        entity.setArticleEntity(articleEntity);
        entity.setReportEntity(reportEntity);
        entity.setContent(request.getComment());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setNews(ArticleReportNews.ENABLE);
        entity = articleReportService.save(entity);
        List<UserAccountEntity> list = accountService.findAccountAdmin();
        if(list.size() > 0){
            UserAccountEntity accountEntity = list.get(0);
            NotificationEntity notificationEntity = new NotificationEntity();
            notificationEntity.setType(NotificationTypeType.REPORT);
            notificationEntity.setAccount(accountEntity);
            notificationEntity.setContent(MessageConstant.MESS_REPORT_POST);
            notificationEntity.setUrl(MessageConstant.URL_REPORT_POST + articleEntity.getId());
            simpMessagingTemplate.convertAndSend("/topic/notification/toAdmin", notificationService.save(notificationEntity));
        }
        return new ResponseEntity<>(new ArticleReportResponse(entity.getId()), HttpStatus.OK);
    }
    private void validateData(ReportRequest request) throws APIException{
        if(request.getArticleId() == null){
            throw new ItemCannotEmptyException("Cannot report now");
        }
        if(request.getTypeReportId() == null){
            throw new ItemCannotEmptyException("Type report cannot empty");
        }
    }
}
