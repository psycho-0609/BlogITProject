package com.finnal.blogit.service.imp;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.entity.RoleEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import com.finnal.blogit.repository.UserAccountRepository;
import com.finnal.blogit.user.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserAccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserAccountEntity> entityOp = accountRepository.findByEmailAndStatus(email, AccountStatus.ENABLE);
        if(!entityOp.isPresent()){
            throw  new UsernameNotFoundException(email);
        }
        UserAccountEntity entity = entityOp.get();
        GrantedAuthority authorities = new SimpleGrantedAuthority(entity.getRole().getName());

        CustomUserDetail customUserDetail = new CustomUserDetail(entity.getEmail(),entity.getPassword(),true,true,true,true, Collections.singleton((SimpleGrantedAuthority) authorities));
        customUserDetail.setHistoryId(entity.getHistory().getId());
        customUserDetail.setFavoriteId(entity.getFavoriteArticle().getId());
        customUserDetail.setReadLaterId(entity.getReadLater().getId());
        customUserDetail.setUserDetail(entity.getUserDetailEntity().getId());
        customUserDetail.setId(entity.getId());
        if(entity.getRole().getName().equals("ADMIN")){
            customUserDetail.setRoleType(Constant.ADMIN_TYPE);
        }else if(entity.getRole().getName().equals("USER")){
            customUserDetail.setRoleType(Constant.USER_TYPE);
        }
        return customUserDetail;

    }
}
