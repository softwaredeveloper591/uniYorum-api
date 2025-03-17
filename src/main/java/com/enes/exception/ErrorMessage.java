package com.enes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	private String ofStatic;
	
	private MessageType messsageType;
	
	public String prepareErrorMessage() {
		StringBuilder builder= new StringBuilder();
		builder.append(messsageType.getMessage());
		if(this.ofStatic!=null) {
			builder.append(" : "+ this.ofStatic);
		}
		return builder.toString();
	}
}
