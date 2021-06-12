package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.FavoriteEntity;
import com.finnal.blogit.repository.FavoriteRepository;
import com.finnal.blogit.service.inter.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteService implements IFavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Override
    public Optional<FavoriteEntity> findByAccountId(Long id) {
        return favoriteRepository.findAllByUserAccount_Id(id);
    }
}
