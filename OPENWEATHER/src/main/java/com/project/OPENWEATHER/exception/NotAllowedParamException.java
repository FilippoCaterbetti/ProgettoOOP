package com.project.OPENWEATHER.exception;


//errore per param
public class NotAllowedParamException extends Exception{
	
	String error;
	
	/**
	 * @param error è il messaggio di errore.
	 */
	public NotAllowedParamException(String error) {
		
		this.error = error;
	}
	
	/**
	 * @return String con messaggio di errore
	 */
	public String getError() {
		return error;
	}
}
