package com.project.OPENWEATHER.exception;

import java.lang.Exception;

/**
 * genera l'eccezione riguardante l'inserimento errato del valore
 *
 */
public class NotAllowedValueException extends Exception {
	String error;

	/**
	 * @param error rappresenta il messaggio di errore.
	 */
	public NotAllowedValueException(String error) {

		this.error = error;
	}

	/*
	 * public NotAllowedValueException(Throwable error) { super(error); }
	 */

	/**
	 * Restituisce un messaggio di errore
	 * 
	 * @return String che contiene il messaggio d'errore
	 */
	public String getError() {

		return error;
	}
}
