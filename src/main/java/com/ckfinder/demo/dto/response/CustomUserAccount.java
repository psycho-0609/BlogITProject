package com.ckfinder.demo.dto.response;

import lombok.Data;

@Data
public class CustomUserAccount {

    private Long id;
    private String email;
    private CustomerUserDetailDTO userDetail;
}
