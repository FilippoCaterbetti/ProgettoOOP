package com.project.OPENWEATHER.exception;

import java.lang.Exception;

/**
 * genera l'eccezione riguardante l'inserimento errato del periodo
 *
 */

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
