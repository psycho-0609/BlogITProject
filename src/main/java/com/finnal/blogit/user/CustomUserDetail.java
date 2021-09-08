package com.finnal.blogit.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUserDetail extends User {

    private Long id;
//    private Long historyId;
//    private Long favoriteId;
//    private Long readLaterId;
    private Long userDetail;
    private Integer roleType;
    public CustomUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

//    public Long getHistoryId() {
//        return historyId;
//    }
//
//    public void setHistoryId(Long historyId) {
//        this.historyId = historyId;
//    }
//
//    public Long getFavoriteId() {
//        return favoriteId;
//    }
//
//    public void setFavoriteId(Long favoriteId) {
//        this.favoriteId = favoriteId;
//    }
//
//    public Long getReadLaterId() {
//        return readLaterId;
//    }
//
//    public void setReadLaterId(Long readLaterId) {
//        this.readLaterId = readLaterId;
//    }

    public Long getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Long userDetail) {
        this.userDetail = userDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
