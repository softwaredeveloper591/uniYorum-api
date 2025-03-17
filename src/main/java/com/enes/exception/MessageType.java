package com.enes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
	
	NOT_ACCEPTABLE_REQUEST("1005", "This request is not acceptable");

	private String code;
	private String message;
	
	

}
