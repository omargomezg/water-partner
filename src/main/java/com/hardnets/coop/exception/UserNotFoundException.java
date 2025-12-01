package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -230837254476948884L;

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException() {
		super("User not found");
	}
}
