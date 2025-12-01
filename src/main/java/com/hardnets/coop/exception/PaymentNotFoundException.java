package com.hardnets.coop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PaymentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1465750655054939863L;

	public PaymentNotFoundException(String token) {
		super(String.format("Payment with token %s cannot found", token));
	}
}
