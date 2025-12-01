package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClientNotFoundException(String rut) {
		super(String.format("Client with rut %s cannot found", rut));
	}

	public ClientNotFoundException() {
		super("Client not found");
	}
}
