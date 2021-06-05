package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.FavoriteArticleEntity;
import com.ckfinder.demo.repository.ArticleRepository;
import com.ckfinder.demo.repository.FavoriteRepository;
import com.ckfinder.demo.repository.FavouriteArticleRepository;
import com.ckfinder.demo.service.inter.IFavoriteArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteArticleService implements IFavoriteArticleService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FavouriteArticleRepository favouriteArticleRepository;

    @Override
    public FavoriteArticleEntity create(Long favId, Long articleId) {
        FavoriteArticleEntity entity = new FavoriteArticleEntity();
        entity.setFavoriteEntity(favoriteRepository.findById(favId).get());
        entity.setArticleEntity(articleRepository.findById(articleId).get());
        return favouriteArticleRepository.save(entity);
    }

    @Override
    public Optional<FavoriteArticleEntity> findByFavIdAndArticleId(Long favId, Long articleId) {
        return favouriteArticleRepository.findByFavoriteEntity_IdAndArticleEntity_Id(favId,articleId);
    }

    @Override
    public Optional<FavoriteArticleEntity> findById(Long id) {
        return favouriteArticleRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        favouriteArticleRepository.deleteById(id);
    }
}
