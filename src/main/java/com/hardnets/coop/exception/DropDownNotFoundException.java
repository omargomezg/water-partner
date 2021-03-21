package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DropDownNotFoundException extends RuntimeException {
    public DropDownNotFoundException(String message) {
        super(message);
    }
}
