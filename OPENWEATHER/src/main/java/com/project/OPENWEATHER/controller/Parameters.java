package com.project.OPENWEATHER.controller;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parameters {
	
	@JsonProperty("città")
	ArrayList<String> citta = new ArrayList<String>();
	int error;
	String value;
	int period;
	
	public Parameters(ArrayList<String> citta, int error, String value, int period) {
		super();
		this.citta = citta;
		this.error = error;
		this.value = value;
		this.period = period;
	}
	
	public Parameters() {
	}

	public ArrayList<String> getCitta() {
		return citta;
	}

	public void setCitta(ArrayList<String> citta) {
		this.citta = citta;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
	
	

}
