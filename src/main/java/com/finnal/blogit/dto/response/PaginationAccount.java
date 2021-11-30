package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginationAccount {

    private Long totalPage;
    private List<CustomUserAccount> accounts;
}
