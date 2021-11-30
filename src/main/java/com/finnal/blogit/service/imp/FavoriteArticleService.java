package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.GetFavArticle;
import com.finnal.blogit.entity.FavoriteArticleEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.repository.ArticleRepository;
import com.finnal.blogit.repository.FavouriteArticleRepository;
import com.finnal.blogit.service.inter.IFavoriteArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteArticleService implements IFavoriteArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FavouriteArticleRepository favouriteArticleRepository;

    @Override
    public FavoriteArticleEntity create(Long accountId, Long articleId) {
        FavoriteArticleEntity entity = new FavoriteArticleEntity();
        entity.setAccount(new UserAccountEntity(accountId));
        entity.setArticleEntity(articleRepository.findById(articleId).get());
        return favouriteArticleRepository.save(entity);
    }

    @Override
    public Optional<FavoriteArticleEntity> findByAccountIdAndArticleId(Long favId, Long articleId) {
        return favouriteArticleRepository.findByAccount_IdAndArticleEntity_Id(favId,articleId);
    }

    @Override
    public Optional<FavoriteArticleEntity> findById(Long id) {
        return favouriteArticleRepository.findById(id);
    }

    @Override
    public Long countByArticleId(Long id) {
        return favouriteArticleRepository.countAllByArticleEntityId(id);
    }

    @Override
    public void deleteById(Long id) {
        favouriteArticleRepository.deleteById(id);
    }

    @Override
    public List<GetFavArticle> getByFavId(Long id) {
        return favouriteArticleRepository.getListFavByAccountId(id);
    }

    @Override
    @Transactional
    public void deleteAllByFavId(Long id) {
//        favouriteArticleRepository.deleteByFavoriteEntityId(id);
    }

    @Override
    public List<GetFavArticle> findForSearch(Long id, String title) {
        return favouriteArticleRepository.getListFavByAccountId(id)
                .stream().filter(el -> el.getArticle().getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Long countTotalFav(List<Long> ids) {
        return favouriteArticleRepository.countTotalFavorite(ids);
    }
}
