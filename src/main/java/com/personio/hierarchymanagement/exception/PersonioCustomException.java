package com.personio.hierarchymanagement.exception;

public class PersonioCustomException extends Exception {
	
	private String errorMessage;
	

	public PersonioCustomException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
