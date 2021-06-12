package com.finnal.blogit.exception.api;

import lombok.Data;

@Data
public class ExceptionResponse {

    private Integer status;
    private String message;

}
