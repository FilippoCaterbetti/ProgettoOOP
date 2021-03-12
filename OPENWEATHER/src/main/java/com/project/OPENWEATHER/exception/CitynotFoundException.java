package com.project.OPENWEATHER.exception;

import java.lang.Exception;


  /**generiamo l'errore se la città è errata o non esiste
   *
   */

public class CitynotFoundException extends Exception{
	
	String error;
	
	/**
	 * @param error rappresenta il messaggio di errore.
	 */
	public CitynotFoundException(String error) {
		
		this.error=error;
	}
	
	/**
	 * @return String con messaggio di errore
	 */
	public String getError() {
		
		return error;
	}
}
