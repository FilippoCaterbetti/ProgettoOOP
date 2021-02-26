package com.project.OPENWEATHER.model;



public class Statistics  {
	
	

	private String main;
	private String description;
	private double temp_max;
	private double temp_min;
	private double temp_avg;
	private double feels_like;
	private double temp;
	private String data;
	
	public Statistics() {
		super();
	}

	/**
	 * @param temp_max
	 * @param temp_min
	 * @param temp_avg
	 * @param feels_like
	 * @param temp
	 * @param data
	 */
	public Statistics(double temp_max, double temp_min, double temp_avg, double feels_like, double temp, String data) {
		super();
		this.temp_max = temp_max;
		this.temp_min = temp_min;
		this.temp_avg = temp_avg;
		this.feels_like = feels_like;
		this.temp = temp;
		this.data = data;
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
	public Statistics(String main, String description, double temp_max, double temp_min, double temp_avg,
			double feels_like, double temp, String data) {
		super();
		this.main = main;
		this.description = description;
		this.temp_max = temp_max;
		this.temp_min = temp_min;
		this.temp_avg = temp_avg;
		this.feels_like = feels_like;
		this.temp = temp;
		this.data = data;
	}
	
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
