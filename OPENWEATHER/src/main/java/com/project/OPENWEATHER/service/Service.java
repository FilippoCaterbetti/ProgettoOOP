package com.project.OPENWEATHER.service;

public interface Service {
	
	public abstract JSONObject getCityWeather(String city);
	public abstract City getTempfromApi(String city);
	public abstract String save(String city) throws invalidException;
	public String FiveHourInfo(String cityName);
}
