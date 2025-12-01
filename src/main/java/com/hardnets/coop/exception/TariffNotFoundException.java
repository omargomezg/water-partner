package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TariffNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7609503800022618961L;

	public TariffNotFoundException(String message) {
		super(message);
	}
}
