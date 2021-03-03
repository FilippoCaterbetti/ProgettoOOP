package com.project.OPENWEATHER.exception;

public class NotAllowedValueException {
	String error;
	
	
	/**
	 * @param mex rappresenta il messaggio di errore.
	 */
	public NotAllowedValueException(String error) {
		
		this.error = error;
	}
	
	/**
	 * Restituisce un messaggio di errore passato al costruttore quando viene inserita una stringa non ammessa per il value.
	 * @return String che contiene il messaggio d'errore che viene stampato.
	 */
	public String getError() {
		return error;
	}
}
