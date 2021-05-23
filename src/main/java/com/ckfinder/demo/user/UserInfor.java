package com.ckfinder.demo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class UserInfor {

    public static CustomUserDetail getPrincipal(){
        System.out.println((SecurityContextHolder.getContext()).getAuthentication().getPrincipal());
        try {
            CustomUserDetail userDetail = (CustomUserDetail) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
            return  userDetail;
        }catch (Exception e){
            return null;
        }

    }
    public static List<String> getAuthorities(){
        List<String> result  = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            result.add(authority.getAuthority());
        }

        return result;
    }
}
