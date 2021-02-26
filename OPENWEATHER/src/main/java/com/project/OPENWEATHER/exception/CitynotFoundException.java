package com.project.OPENWEATHER.Exception;

import java.lang.Exception;


//generiamo questo errore se la città è errata o non esiste

public class CitynotFoundException extends Exception{
	String error;
	
	public CitynotFoundException(String error) {
		this.error=error;
	}
	
	public String getError() {
		return error;
	}
}
