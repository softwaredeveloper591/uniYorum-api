package com.enes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
	
	NOT_ACCEPTABLE_REQUEST("1005", "This request is not acceptable"),
	USERNAME_OR_PASSWORD_INVALID("1000", "Username or password Wrong");

	private String code;
	private String message;
	
	

}
