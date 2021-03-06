package com.project.OPENWEATHER.model;

import java.util.Collection;
import java.util.Date;
import org.json.JSONObject;

public class Temperature implements  Cloneable { //JSONClass,
	
	
	//Group of weather parameters (Rain, Snow, Extreme etc.)
	private String main;
	
	//Weather condition within the group
	private String description;
	
	//Temperature
	private double temp;
	
	//Maximum temperature at the moment of calculation
	private double temp_max;
	
	//Minimum temperature at the moment of calculation
	private double temp_min;
	
	//Average temperature
	private double temp_avg;
	
	//This temperature parameter accounts for the human perception of weather
	private double feels_like;

	//The date at the moment of calculation from library java.util.Date;
	String data;
	

	
	
	/** 
	 * 
	 * Costruttore
	 * 
     */
	public Temperature() {
		this.main = null;
		this.description = null;
		this.temp = 0;
		//this.temp_avg = 0;
		this.temp_max = 0;
		this.temp_min = 0;
		this.feels_like = 0;
		this.data = null;
	}
	/**
	 * 
	 * @param main
	 * @param description
	 * @param temp_max
	 * @param temp_min
	 * @param temp_avg
	 * @param feels_like
	 * @param temp
	 * @param data
	 * 
	 */
	public Temperature(String main, String description, double temp_max, double temp_min, double temp_avg,
			double feels_like, double temp, String data) {
		this.temp = temp;
		this.feels_like = feels_like;
		this.temp_max = temp_max;
		this.temp_min = temp_min;
		//this.temp_avg = avg.getAvg();
		this.data = data;
		this.main = main;
		this.description = description;
	}

		
	
	/**
	 * Costruttore per Temperature
	 * 
	 * @param datoMeteo JSONObject da parsare per costruire il dato meteo
	 * 
	 * Il JSONObject deve avere questa struttura
	 * "temp" 			temperatura
	 * "feels_like"  	temperatura percepita	
	 * "temp_max"		temperatura massima
	 * "temp_min"		temperatura minima
	 * "temp_avg"		temperatura media
	 * "data"			data della misurazione 
	 * 
	 */
	public Temperature(JSONObject temperature) {
		this.temp = Double.parseDouble(temperature.get("temp").toString()); //oppure (?) aggiungere alla fine .toString() Penso siano la stessa cosa
		this.feels_like = Double.parseDouble(temperature.get("feels_like").toString());
		this.temp_max = Double.parseDouble(temperature.get("temp_max").toString());
		this.temp_min = Double.parseDouble(temperature.get("temp_min").toString());	
		//this.temp_avg = avg.getAvg();    
		this.data = temperature.get("data").toString();
		this.main = temperature.get("main").toString();
		this.description = temperature.get("description").toString();
	}	
	
	/**
	 * JSONObject per ottenere con tutti i dati del rilevamento
	 * 
	 * @return JSONObject con i dati meteo
	 */
	public JSONObject toJSONObject() {
		
		JSONObject t = new JSONObject();
		
		t.put("temp", this.temp);
		t.put("feels_like", this.feels_like);
		t.put("temp_max", this.temp_max);
		t.put("temp_min", this.temp_min);
		t.put("temp_avg", this.temp_avg);
		t.put("data", this.data);
		t.put("main", this.main);
		t.put("description", this.description);
		
		return t;
	}
	

	/**
	 * Creazione di una copia delle temperature
	 * 
	 * @return una copia delle temperature
	 */
	public Temperature clone() {
		
		return new Temperature (this.toJSONObject()  );
		
	}
	
	//JSONObject clone = new JSONObject(Temperature.toString());
	
	
	
	

	/**
	 * @return the main
	 */
	public String getMain() {
		return main;
	}

	/**
	 * @param main the main to set
	 */
	public void setMain(String main) {
		this.main = main;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the temp_max
	 */
	public double getTemp_max() {
		return temp_max;
	}

	/**
	 * @param temp_max the temp_max to set
	 */
	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}

	/**
	 * @return the temp_min
	 */
	public double getTemp_min() {
		return temp_min;
	}

	/**
	 * @param temp_min the temp_min to set
	 */
	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}

	/**
	 * @return the temp_avg
	 */
	public double getTemp_avg() {
		return temp_avg;
	}

	/**
	 * @param temp_avg the temp_avg to set
	 */
	public void setTemp_avg(double temp_avg) {
		this.temp_avg = temp_avg;
	}

	/**
	 * @return the feels_like
	 */
	public double getFeels_like() {
		return feels_like;
	}

	/**
	 * @param feels_like the feels_like to set
	 */
	public void setFeels_like(double feels_like) {
		this.feels_like = feels_like;
	}

	/**
	 * @return the temp
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Statistics [main=" + main + ", description=" + description + ", temp_max=" + temp_max + ", temp_min="
				+ temp_min + ", temp_avg=" + temp_avg + ", feels_like=" + feels_like + ", temp=" + temp + ", data="
				+ data + "]";
	}
	

}
