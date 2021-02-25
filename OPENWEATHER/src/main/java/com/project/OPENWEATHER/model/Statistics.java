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
	
			//CONTROLLARE USO SUPER!!!
	
	public Statistics() {
		super();
	}
	
	public Statistics(double temp_max, double temp_min, double temp_avg,double feels_like, double temp,String data, String main, String description) {
		this.main = main;
		this.description = description;
		this.temp_max=temp_max;
		this.temp_min=temp_min;
		this.temp_avg=temp_avg;
		this.feels_like=feels_like;
		this.temp=temp;
		this.data=data;
	}
	
	public Statistics(double temp_max, double temp_min, double temp_avg,double feels_like, double temp, String data) {
		this.temp_max=temp_max;
		this.temp_min=temp_min;
		this.temp_avg=temp_avg;
		this.feels_like=feels_like;
		this.temp=temp;
		this.data=data;
	}
	
	public double getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}

	public double getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}

	public double getTemp_avg() {
		return temp_avg;
	}

	public void setTemp_avg(double temp_avg) {
		this.temp_avg = temp_avg;
	}

	public double getFeels_like() {
		return feels_like;
	}

	public void setFeels_like(double feels_like) {
		this.feels_like = feels_like;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Statistics [main=" + main + ", description=" + description + ", temp_max=" + temp_max + ", temp_min="
				+ temp_min + ", temp_avg=" + temp_avg + ", feels_like=" + feels_like + ", temp=" + temp + ", data="
				+ data + "]";
	}
	
}
