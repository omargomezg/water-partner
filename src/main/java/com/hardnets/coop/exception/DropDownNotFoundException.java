package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DropDownNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 801233520387875485L;

	public DropDownNotFoundException(String message) {
		super(message);
	}
}
