package com.finnal.blogit.service.imp;


import com.finnal.blogit.dto.response.CustomUserAccount;
import com.finnal.blogit.entity.*;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import com.finnal.blogit.dto.request.RegisterRequest;
import com.finnal.blogit.service.inter.IUserAccountService;
import com.finnal.blogit.repository.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        accountEntity.setRole(repository.findByName("USER"));
        accountEntity.setStatus(AccountStatus.DISABLE);
        accountEntity.setToken(RandomString.make(45));
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
    public Optional<UserAccountEntity> findByUsername(String username) {
        return userAccountRepository.findByEmail(username);
    }

    @Override
    public UserAccountEntity updateResetToken(UserAccountEntity userAccountEntity, String token) {
        userAccountEntity.setToken(token);
        return userAccountRepository.save(userAccountEntity);
    }

    @Override
    public Optional<UserAccountEntity> findByToken(String token) {
        return userAccountRepository.findByToken(token);
    }

    @Override
    public Optional<UserAccountEntity> findById(Long id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public UserAccountEntity save(UserAccountEntity entity) {
        return userAccountRepository.save(entity);
    }

    @Override
    public Optional<UserAccountEntity> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<UserAccountEntity> findByEmailAndStatus(String email, AccountStatus status) {
        return userAccountRepository.findByEmailAndStatus(email, status);
    }

    @Override
    public List<CustomUserAccount> getAllAccountUser() {
        return userAccountRepository.findAllUser();
    }

    @Override
    public Optional<CustomUserAccount> findOneById(Long id) {
        return userAccountRepository.findOneById(id);
    }

}
