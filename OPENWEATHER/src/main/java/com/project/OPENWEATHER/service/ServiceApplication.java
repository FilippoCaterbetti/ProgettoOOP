package com.project.OPENWEATHER.service;

public class ServiceApplication implements Service {
	
	 
	private String api_key = "069782b0f7fc9729e6c7151ffd1448ed";

	@Override
	public JSONObject getCityWeather(String city) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public City getTempfromApi(String city) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public String save(String city) throws invalidException {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public String saveEveryfiveHour(String cityName) {
		// TODO Auto-generated method stub
		
		
		return null;
	}
	
	
	/**
	 * Questo metodo va a prendere da OpenWeather le previsioni meteo di una città.
	 * @param è il nome della città di cui si vuole conoscere le previsioni meteo.
	 * @return un JSONObject contenente le previsioni meteo complete.
	 */
	
}