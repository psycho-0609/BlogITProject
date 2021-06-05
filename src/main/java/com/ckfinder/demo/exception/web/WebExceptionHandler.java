package com.ckfinder.demo.exception.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(WebException.class)
    public String articleNotFound(WebException exc){
        return "404";
    }
}
