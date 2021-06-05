package com.ckfinder.demo.exception.api;

public class ItemNotFoundException extends APIException{
    public ItemNotFoundException() {
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
