package com.ckfinder.demo.service.inter;

import com.ckfinder.demo.entity.HistoryArticleEntity;

public interface IHistoryArticleService {
    void insert(Long hisId,Long articleId);
    void delete(Long articleId);
    void deleteAllByHistoryId();

}
