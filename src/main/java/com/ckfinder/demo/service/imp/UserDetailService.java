package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.entity.RoleEntity;
import com.ckfinder.demo.entity.UserAccountEntity;
import com.ckfinder.demo.repository.UserAccountRepository;
import com.ckfinder.demo.user.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserAccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccountEntity entity = accountRepository.findByEmail(email);
        if(entity == null){
            throw  new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(RoleEntity roleEntity:entity.getRoles()){
            authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
        }
        return new CustomUserDetail(entity.getEmail(),entity.getPassword(),true,true,true,true,authorities);
    }
}
