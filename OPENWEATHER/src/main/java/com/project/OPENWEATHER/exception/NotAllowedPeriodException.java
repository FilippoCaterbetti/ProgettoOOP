package com.project.OPENWEATHER.exception;


//contiene eccezione per un periodo non valido
//periodi ammessi: giornaliero , settimanale, mensile


public class NotAllowedPeriodException {
	String error;
	
	public NotAllowedPeriodException(String error) {
		this.error= error;
	}
	
	public String getError() {
		return error;
	}
}
