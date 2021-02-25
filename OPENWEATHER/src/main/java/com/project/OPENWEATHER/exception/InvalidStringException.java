package com.project.OPENWEATHER.exception;

//errore generato da stringa non regolare

public class InvalidStringException {
	
	String error;
	
	public InvalidStringException(String error) {
		this.error=error;
	}
	
	public String getError() {
		return error;
	}
}
