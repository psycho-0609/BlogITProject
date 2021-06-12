package com.finnal.blogit.exception.api;

public class ItemCannotEmptyException extends APIException{
    public ItemCannotEmptyException() {
        super();
    }

    public ItemCannotEmptyException(String message) {
        super(message);
    }
}
