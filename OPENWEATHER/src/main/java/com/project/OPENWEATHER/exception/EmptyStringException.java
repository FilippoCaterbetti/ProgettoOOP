package com.project.OPENWEATHER.exception; 

   /**
    * generiamo l'eccezione a causa di una stringa vuota 
    *
    */
public class EmptyStringException extends Exception {
	
	String error;
	
	/**
	 * @param error rappresenta il messaggio di errore.
	 */
	public EmptyStringException(String error) {
		this.error = error;
	}
	
	/**
	 * Restituisce un messaggio di errore 
	 * @return String che contiene il messaggio d'errore
	 */
	public String getError() {
		return error;
	}

}
