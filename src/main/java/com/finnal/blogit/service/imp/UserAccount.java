package com.finnal.blogit.service.imp;

import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.repository.UserAccountRepository;
import com.finnal.blogit.service.inter.IUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccount implements IUserAccount {

    @Autowired
    private UserAccountRepository repository;

    @Override
    public Iterable<UserAccountEntity> findAll() {
        return null;
    }

    @Override
    public UserAccountEntity createUserAccountEntity(UserAccountEntity entity) {

        return null;
    }

    @Override
    public Optional<UserAccountEntity> findById(Long id) {
        return Optional.empty();
    }
}
