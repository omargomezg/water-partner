package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WaterMeterException extends RuntimeException {
    public WaterMeterException(String message) {
        super(message);
    }
}
