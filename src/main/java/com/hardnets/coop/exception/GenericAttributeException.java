package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GenericAttributeException extends RuntimeException {
    public GenericAttributeException(String message) {
        super(message);
    }

    public GenericAttributeException() {
        super();
    }
}
