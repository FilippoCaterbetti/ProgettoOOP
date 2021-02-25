package com.project.OPENWEATHER.Exception;

import java.lang.Exception;

public class CitynotFoundException extends Exception{
	String error;
	
	public CitynotFoundException(String error) {
		this.error=error;
	}
	
	public String getError() {
		return error;
	}
}
