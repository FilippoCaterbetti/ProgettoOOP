package com.project.OPENWEATHER.model;

import java.util.Date;

import org.json.JSONObject;

public class Temperature{
	
	
	//Group of weather parameters (Rain, Snow, Extreme etc.)
	private String main;
	
	//Weather condition within the group
	private String description;
	
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

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

	
	//The "dd/mm/yyyy" at the moment of calculation from library java.util.Date;
	Date data;
	
	public Temperature() {
		super();
	}
	
	/**
	 * @param main
	 * @param description
	 * @param temp_max
	 * @param temp_min
	 * @param temp_avg
	 * @param feels_like
	 * @param temp
	 * @param data
	 */
	public Temperature(String main, String description, double temp_max, double temp_min, double temp_avg,
			double feels_like, double temp, long data) {
		super();
		this.main = main;
		this.description = description;
		this.temp_max = temp_max;
		this.temp_min = temp_min;
		this.temp_avg = temp_avg;
		this.feels_like = feels_like;
		this.temp = temp;
		this.data = new Date(data);
	}

	/**
	 * Costruttore per DatoMeteo
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
		this.temp = Double.parseDouble((String)temperature.get("temp")); //oppure (?) aggiungere alla fine .toString() Penso siano la stessa cosa
		this.feels_like = Double.parseDouble((String)temperature.get("feels_like"));
		this.temp_max = Double.parseDouble((String)temperature.get("temp_max"));
		this.temp_min = Double.parseDouble((String)temperature.get("temp_min"));
		this.temp_avg = (this.temp_min+this.temp_max)/2; 			
		this.data = new Date((long)temperature.get("data"));
	}
	//45 datameteo 178 citta

	

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

	@Override
	public String toString() {
		return "Statistics [main=" + main + ", description=" + description + ", temp_max=" + temp_max + ", temp_min="
				+ temp_min + ", temp_avg=" + temp_avg + ", feels_like=" + feels_like + ", temp=" + temp + ", data="
				+ data + "]";
	}

}
