package com.finnal.blogit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUserAccount {

    private Long id;
    private String email;
    private CustomerUserDetailDTO userDetail;

    public CustomUserAccount(){}

    public CustomUserAccount(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
