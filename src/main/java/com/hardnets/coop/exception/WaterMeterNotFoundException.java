package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class WaterMeterNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8320493886950727114L;

	public WaterMeterNotFoundException(String message) {
		super(message);
	}

	public WaterMeterNotFoundException() {
	}
}
