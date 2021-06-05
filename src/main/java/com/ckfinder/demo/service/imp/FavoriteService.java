package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.FavoriteEntity;
import com.ckfinder.demo.repository.FavoriteRepository;
import com.ckfinder.demo.service.inter.IFavoriteService;
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
