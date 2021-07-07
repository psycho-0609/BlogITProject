package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class ChangeStatusAccountRequest {

    private Long id;
    private Integer status;
}
