package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.HistoryArticleEntity;
import com.ckfinder.demo.repository.ArticleRepository;
import com.ckfinder.demo.repository.HistoryArticleRepository;
import com.ckfinder.demo.repository.HistoryRepository;
import com.ckfinder.demo.service.inter.IHistoryArticleService;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class HistoryArticleService implements IHistoryArticleService {

    @Autowired
    private HistoryArticleRepository historyArticleRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void insert(Long hisId, Long articleId) {
        Optional<HistoryArticleEntity> historyArticleOp = historyArticleRepository.findByHistoryEntity_IdAndArticleEntity_IdAndCratedDate(hisId,articleId,new Date());
        HistoryArticleEntity historyArticleEntity;
        if(historyArticleOp.isPresent()){
            historyArticleEntity = historyArticleOp.get();
        }else{
            historyArticleEntity =  new HistoryArticleEntity();
            historyArticleEntity.setArticleEntity(articleRepository.findById(articleId).get());
            historyArticleEntity.setHistoryEntity(historyRepository.findById(hisId).get());
        }
        historyArticleEntity.setCratedDate(new Date());
        historyArticleRepository.save(historyArticleEntity);

    }

    @Override
    public void delete(Long articleId) {
        CustomUserDetail customUserDetail = UserInfor.getPrincipal();
        HistoryArticleEntity historyArticleEntity = historyArticleRepository.findByArticleEntity_IdAndHistoryEntity_Id(articleId,customUserDetail.getHistoryId());
        historyArticleRepository.delete(historyArticleEntity);
    }

    @Override
    public void deleteAll() {
        CustomUserDetail customUserDetail = UserInfor.getPrincipal();
        historyArticleRepository.deleteAllByHistoryEntity_Id(customUserDetail.getHistoryId());
    }

    @Override
    public Long countAllByHistoryId(Long id) {
        return historyArticleRepository.countAllByHistoryEntityId(id);
    }


}
