package com.project.OPENWEATHER.model;


import java.util.Vector;

import org.json.JSONObject;
import org.json.simple.JSONArray;

public class JSONClass {
	
	City city = new City();
	
	
	/**
	 * 
	 * JSONObject  oggetto City
	 * 
	 * @param name della città in JSONObject.
	 * @return il JSONObject  della città.
	 * 
	 */
	public JSONObject JSONClass(City city) {
		
		
		JSONObject object = new JSONObject();
		
		object.put("name", city.getName());
		object.put("country", city.getCountry());
		object.put("id", city.getId());
		
		JSONArray arr = new JSONArray();
		
		for(int i=0; i<(city.getTemps()).size(); i++) {
			
			JSONObject ow = new JSONObject();
			
			ow.put("temp", (city.getTemps().get(i).getTemp()));
			ow.put("feels_like", (city.getTemps()).get(i).getFeels_like());
			ow.put("temp_max", (city.getTemps()).get(i).getTemp_max());
			ow.put("temp_min", (city.getTemps()).get(i).getTemp_min());
			ow.put("data", (city.getTemps()).get(i).getData());
			ow.put("main", (city.getTemps()).get(i).getMain());
			ow.put("description", (city.getTemps()).get(i).getDescription());
			arr.add(ow);
			
		}
		
		
		object.put("OpenWeather", arr);
		
		return object;
	}
	

}
