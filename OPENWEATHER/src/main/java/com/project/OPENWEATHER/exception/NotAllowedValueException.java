package com.project.OPENWEATHER.exception;

import java.lang.Exception;

public class NotAllowedValueException extends Exception{
	String error;
	
	
	/**
	 * @param error rappresenta il messaggio di errore.
	 */
	public NotAllowedValueException(String error) {
		
		this.error = error;
	}
	

    /*public NotAllowedValueException(Throwable error) {
        super(error);
    }*/
	
	/**
	 * Messaggio di errore quando viene inserita una stringa errata per il value.
	 * @return String con il messaggio d'errore
	 */
	public String getError() {
		return error;
	}
}
