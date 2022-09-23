package com.training.exception;

public class ElementNotFoundException extends Exception {

private String errorCode,errorMessage;
	
	public ElementNotFoundException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}
	
	@Override
	public String getMessage() {
		return this.errorCode+":"+super.getMessage();
	}
}
