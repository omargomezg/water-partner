package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public UserException() {
        super("User exception");
    }
}
