package com.project.OPENWEATHER.exception;

import java.lang.Exception;

//errore generato da stringa non regolare

public class InvalidStringException extends Exception{
	
	String error;
	
	public InvalidStringException(String error) {
		this.error=error;
	}
	
	public String getError() {
		return error;
	}
}
