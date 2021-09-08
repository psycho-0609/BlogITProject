package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.GetHistoryFlowDate;
import com.finnal.blogit.dto.response.HistoryArticleDTO;
import com.finnal.blogit.entity.HistoryArticleEntity;

import java.util.List;
import java.util.Optional;

public interface IHistoryArticleService {
    void insert(Long accountId,Long articleId);
    void delete(Long id);
    void deleteAll();
    Long countAllByAccountId(Long id);
    Optional<HistoryArticleEntity> findById(Long id);
    List<GetHistoryFlowDate> getHistoryArticleFlowDate(Long id);
    List<GetHistoryFlowDate> getAllForSearch(Long id, String title);

}
