package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(String id) {
        super(String.format("Client with rut %s cannot found", id));
    }
}
