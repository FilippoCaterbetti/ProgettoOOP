package com.project.OPENWEATHER.model;

public class City {
	
	//Implementazione costrutttori
	
	private String name;
	private String country;
	private long id;
	private double temp;
	
	public City() {
		super();
		
	}
	
	public City (long id) {
		
		this.id=id;
		this.name=null;
		this.country=null;
			
	}
	
	public City (String name){
		this.id=0;
		this.name=null;
		this.country=null;
	}
	
	public City (String name, String country) {
		
		this.id=0;
		this.name=name;
		this.country=country;
			
	}
	
	public City (String name, String country, long id ) {
		
		this.id=id;
		this.name=name;
		this.country=country;
			
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public double getTemp() {
		return temp;
	}
	
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	@Override
	public String toString() {
		return "City [name=" + name + ", country=" + country + ", id=" + id + ", temp=" + temp + "]";
	}
					
}