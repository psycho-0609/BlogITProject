package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.GetHistoryFlowDate;
import com.finnal.blogit.dto.response.HistoryArticleDTO;
import com.finnal.blogit.entity.HistoryArticleEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.repository.ArticleRepository;
import com.finnal.blogit.repository.HistoryArticleRepository;
import com.finnal.blogit.service.inter.IHistoryArticleService;
import com.finnal.blogit.user.CustomUserDetail;
import com.finnal.blogit.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryArticleService implements IHistoryArticleService {

    @Autowired
    private HistoryArticleRepository historyArticleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void insert(Long accountId, Long articleId) {
        Optional<HistoryArticleEntity> historyArticleOp = historyArticleRepository.findByAccount_IdAndArticleEntity_IdAndCratedDate(accountId, articleId, new Date());
        HistoryArticleEntity historyArticleEntity;
        if (historyArticleOp.isPresent()) {
            historyArticleEntity = historyArticleOp.get();
        } else {
            historyArticleEntity = new HistoryArticleEntity();
            historyArticleEntity.setArticleEntity(articleRepository.findById(articleId).get());
            historyArticleEntity.setAccount(new UserAccountEntity(accountId));
        }
        historyArticleEntity.setCratedDate(new Date());
        historyArticleEntity.setTimeWatch(LocalDateTime.now());
        historyArticleRepository.save(historyArticleEntity);
    }

    @Override
    public void delete(Long id) {
        historyArticleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        CustomUserDetail customUserDetail = UserInfor.getPrincipal();
        historyArticleRepository.deleteAllByAccount_Id(customUserDetail.getId());
    }

    @Override
    public Long countAllByAccountId(Long id) {
        return historyArticleRepository.countAllByAccountId(id);
    }

    @Override
    public Optional<HistoryArticleEntity> findById(Long id) {
        return historyArticleRepository.findById(id);
    }

    @Override
    public List<GetHistoryFlowDate> getHistoryArticleFlowDate(Long id) {
        List<HistoryArticleDTO> historyLists = historyArticleRepository.findAllByAccountId(id);
        return getListHistoryArticle(historyLists);
    }

    @Override
    public List<GetHistoryFlowDate> getAllForSearch(Long id, String title) {
        List<HistoryArticleDTO> historyLists = historyArticleRepository.findAllForSearch(id, title);
        return getListHistoryArticle(historyLists);
    }

    private List<GetHistoryFlowDate> getListHistoryArticle(List<HistoryArticleDTO> historyLists) {
        List<Date> listDate = historyLists.stream().map(HistoryArticleDTO::getCreatedDate).distinct().collect(Collectors.toList());
        List<GetHistoryFlowDate> lists = new ArrayList<>();
        listDate.forEach(date -> {
            GetHistoryFlowDate el = new GetHistoryFlowDate();
            el.setDate(date);
            el.setHistoryArticle(historyLists.stream().filter(item -> item.getCreatedDate().equals(date)).collect(Collectors.toList()));
            lists.add(el);
        });
        return lists;
    }


}
