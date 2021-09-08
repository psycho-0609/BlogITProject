package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.GetFavArticle;
import com.finnal.blogit.entity.FavoriteArticleEntity;

import java.util.List;
import java.util.Optional;

public interface IFavoriteArticleService {

    FavoriteArticleEntity create(Long accountId, Long articleId);

    Optional<FavoriteArticleEntity> findByAccountIdAndArticleId(Long accountId, Long articleId);
    Optional<FavoriteArticleEntity> findById(Long id);
    Long countByArticleId(Long id);
    void deleteById(Long id);
    List<GetFavArticle> getByFavId(Long id);
    void deleteAllByFavId(Long id);
    List<GetFavArticle> findForSearch(Long id, String title);
    Long countTotalFav(List<Long> ids);

}
