package com.ckfinder.demo.exception.api;

public class ItemCannotEmptyException extends APIException{
    public ItemCannotEmptyException() {
        super();
    }

    public ItemCannotEmptyException(String message) {
        super(message);
    }
}
