package com.project.OPENWEATHER.exception;

import java.lang.Exception;

//contiene eccezione per un periodo non valido
//periodi ammessi: giornaliero , settimanale, mensile


public class NotAllowedPeriodException extends Exception {
	String error;
	
	
	/**
	 * @param error è il messaggio di errore.
	 */
	public NotAllowedPeriodException(String error) {
		this.error= error;
	}
	
	
	/**
	 * @return String con messaggio di errore
	 */
	public String getError() {
		return error;
	}
}
