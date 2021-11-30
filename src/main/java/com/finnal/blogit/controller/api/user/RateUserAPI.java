package com.finnal.blogit.controller.api.user;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.request.RateRequest;
import com.finnal.blogit.dto.response.MessageDTO;
import com.finnal.blogit.dto.response.RateDTO;
import com.finnal.blogit.dto.response.UserInforAccountDTO;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.RateEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.ScoreRateType;
import com.finnal.blogit.exception.api.APIException;
import com.finnal.blogit.exception.api.ItemCannotEmptyException;
import com.finnal.blogit.exception.api.ItemNotFoundException;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.service.inter.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user/rate")
public class RateUserAPI {

    @Autowired
    private IRateService service;

    @Autowired
    private IArticleService aService;

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createOrUpdate(@RequestBody RateRequest request, HttpSession session) throws APIException {
        Long accountId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        ArticleEntity article = aService.findById(request.getArticleId()).orElseThrow(() -> new ItemNotFoundException("Post is not found"));
        if (request.getScore() > 5 || request.getScore() <= 0) {
            throw new ItemCannotEmptyException("Score is invalid");
        }
        RateEntity rateEntity = service.findOneByAccountIdAndArticleId(accountId, request.getArticleId()).orElse(new RateEntity());
        setParam(rateEntity, request, accountId);
        service.save(rateEntity);
        double avgScore = Math.round(service.totalScore(request.getArticleId()) / service.countByArticleId(request.getArticleId()));
        article.setAvgRate(avgScore);
        aService.save(article);
        return new ResponseEntity<>(new MessageDTO("Success"), HttpStatus.OK);
    }

    @GetMapping("/getOne")
    public ResponseEntity<RateDTO> getOne(@RequestParam("postId") Long id, HttpSession session) throws APIException {
        Long accountId = ((UserInforAccountDTO) session.getAttribute(Constant.USER)).getId();
        if (!aService.isExistedById(id)) {
            throw new ItemNotFoundException("Post is not found");
        }
        return new ResponseEntity<>(service.getOneByAccountIdAndArticleId(accountId, id).orElse(new RateDTO()), HttpStatus.OK);
    }

    private void setParam(RateEntity rateEntity, RateRequest request, Long accountId) {
        rateEntity.setAccount(new UserAccountEntity(accountId));
        rateEntity.setArticle(new ArticleEntity(request.getArticleId()));
        rateEntity.setScore(ScoreRateType.fromValue(request.getScore()));
    }
}
