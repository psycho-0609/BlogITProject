package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUserAccount {

    private Long id;
    private String email;
    private AccountStatus status;
    private CustomerUserDetailDTO userDetail;

    public CustomUserAccount(){}

    public CustomUserAccount(Long id, String email, CustomerUserDetailDTO userDetail) {
        this.id = id;
        this.email = email;
        this.userDetail = userDetail;
    }

    public CustomUserAccount(Long id, String email, AccountStatus status, Long detailId, String lastName, String firstName, String thumbnail){
        this.id = id;
        this.email = email;
        this.status = status;
        this.userDetail = new CustomerUserDetailDTO(detailId,lastName,firstName,thumbnail);
    }

    public CustomUserAccount(Long id, String email, AccountStatus status) {
        this.id = id;
        this.email = email;
        this.status = status;
    }

    public Integer getStatus() {
        return status == null ? null : status.getValue();
    }
}
