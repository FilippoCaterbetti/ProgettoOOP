package com.project.OPENWEATHER.exception;

import java.lang.Exception;

//contiene eccezione per un periodo non valido
//periodi ammessi: giornaliero , settimanale, mensile


public class NotAllowedPeriodException extends Exception {
	String error;
	
	public NotAllowedPeriodException(String error) {
		this.error= error;
	}
	
	public String getError() {
		return error;
	}
}
