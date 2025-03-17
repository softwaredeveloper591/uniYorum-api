package com.enes.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class IncorrectRequestException extends RuntimeException {
	
	private HttpStatus statusCode;

    public IncorrectRequestException(String message) {
        super(message);
        this.statusCode = HttpStatus.BAD_REQUEST;
    }

    public IncorrectRequestException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
