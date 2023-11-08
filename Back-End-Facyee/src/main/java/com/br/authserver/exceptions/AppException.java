package com.br.authserver.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus httpStatus;
	
	public AppException(String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	

}
