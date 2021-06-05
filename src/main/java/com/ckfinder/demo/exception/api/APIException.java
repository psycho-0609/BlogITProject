package com.ckfinder.demo.exception.api;

public abstract class APIException extends Exception{

    public APIException() {
        super();
    }

    public APIException(String message) {
        super(message);
    }
}
