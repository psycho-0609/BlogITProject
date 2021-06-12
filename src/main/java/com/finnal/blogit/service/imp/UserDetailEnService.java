package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.UserDetailEntity;
import com.finnal.blogit.repository.UserDetailRepository;
import com.finnal.blogit.service.inter.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailEnService implements IUserDetailService {

    @Autowired
    private UserDetailRepository repository;

    @Override
    public Optional<UserDetailEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserDetailEntity save(UserDetailEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<UserDetailEntity> findByAccountId(Long id) {
        return repository.findByUserAccountId(id);
    }
}
