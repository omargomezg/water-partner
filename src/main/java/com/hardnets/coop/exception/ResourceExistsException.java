package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceExistsException extends RuntimeException {

	private static final long serialVersionUID = 2467500263276505685L;

	public ResourceExistsException(String message) {
		super(message);
	}
}
