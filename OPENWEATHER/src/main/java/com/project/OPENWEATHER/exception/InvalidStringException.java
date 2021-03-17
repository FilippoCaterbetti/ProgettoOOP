package com.project.OPENWEATHER.exception;

import java.lang.Exception;

/**
 * errore generato da stringa non regolare
 *
 */
public class InvalidStringException extends Exception {

	String error;

	/**
	 * @param error è il messaggio di errore.
	 */
	public InvalidStringException(String error) {

		this.error = error;
	}

	/**
	 * @return String con messaggio di errore
	 */
	public String getError() {

		return error;
	}
}
