package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.UserAccountEntity;
import com.ckfinder.demo.repository.UserAccountRepository;
import com.ckfinder.demo.service.inter.IUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
