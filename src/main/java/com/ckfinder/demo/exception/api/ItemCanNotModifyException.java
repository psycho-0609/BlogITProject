package com.ckfinder.demo.exception.api;

public class ItemCanNotModifyException extends APIException{

    public ItemCanNotModifyException() {
        super();
    }

    public ItemCanNotModifyException(String message) {
        super(message);
    }
}
