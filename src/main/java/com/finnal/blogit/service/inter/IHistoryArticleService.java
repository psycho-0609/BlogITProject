package com.finnal.blogit.service.inter;

public interface IHistoryArticleService {
    void insert(Long hisId,Long articleId);
    void delete(Long articleId);
    void deleteAll();
    Long countAllByHistoryId(Long id);

}
