package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ItemException extends RuntimeException {
    public ItemException(String message) {
        super(message);
    }

    public ItemException() {
        super();
    }
}
