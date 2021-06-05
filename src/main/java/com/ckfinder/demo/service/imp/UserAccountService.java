package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.*;
import com.ckfinder.demo.repository.*;
import com.ckfinder.demo.dto.request.RegisterRequest;
import com.ckfinder.demo.service.inter.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserAccountService implements IUserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository repository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ReadLaterRepository readLaterRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;



    @Override
    @Transactional
    public UserAccountEntity registryAccount(RegisterRequest request) {

        UserAccountEntity accountEntity = new UserAccountEntity();
        UserDetailEntity userDetail = new UserDetailEntity();
        userDetail.setFirstName(request.getFirstName());
        userDetail.setLastName(request.getLastName());
        accountEntity.setEmail(request.getEmail());
        accountEntity.setPassword(encoder.encode(request.getPassword()));
        accountEntity.setRoles(Arrays.asList(repository.findByName("USER")));
        UserAccountEntity entitySaved = userAccountRepository.save(accountEntity);

        ReadLaterEntity readLaterEntity = new ReadLaterEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        readLaterEntity.setUserAccount(entitySaved);
        historyEntity.setUserAccount(entitySaved);
        favoriteEntity.setUserAccount(entitySaved);
        favoriteRepository.save(favoriteEntity);
        historyRepository.save(historyEntity);
        readLaterRepository.save(readLaterEntity);
        userDetail.setUserAccount(entitySaved);
        userDetailRepository.save(userDetail);


        return entitySaved;

    }

    @Override
    public UserAccountEntity findByUsername(String username) {
        return userAccountRepository.findByEmail(username);
    }
}
