package com.de013.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestException extends RuntimeException{
    private static final Long serialVersionUID = 1L;
    
    public RestException(String message) {
		super(message);
	}

	public RestException(String status, String message) {
		 super(status, new Throwable(message));
	}
}
