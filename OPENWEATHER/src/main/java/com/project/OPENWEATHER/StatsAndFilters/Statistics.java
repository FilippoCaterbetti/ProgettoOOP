package com.project.OPENWEATHER.StatsAndFilters;

import java.util.Vector;

import org.json.JSONObject;

import com.project.OPENWEATHER.error.ErrorCalculator;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.service.ServiceApplication;

public class Statistics extends ErrorCalculator{
	ServiceApplication service = new ServiceApplication();
	 
	public JSONObject todayAvg(String next) {
		
	City city = new City(next);
	 
     city = service.getTempFutureApi(next);
     
     double temp_max_avg = 0;
     double temp_min_avg = 0;
     double feels_like_avg = 0;
     double variance = 0;
     
     int i=0;
     
     String date = "";
     date += (city.getTemps().get(0).getData()).charAt(8);
     date += (city.getTemps().get(0).getData()).charAt(9);
 
     String effectiveDate = date;
     while(date.equals(effectiveDate)) {
         temp_max_avg += city.getTemps().get(i).getTemp_max();
         temp_min_avg += city.getTemps().get(i).getTemp_min();
         feels_like_avg += city.getTemps().get(i).getFeels_like();
         
         i++;
         effectiveDate = "";
         effectiveDate += (city.getTemps().get(i).getData()).charAt(8);
         effectiveDate += (city.getTemps().get(i).getData()).charAt(9);
     }
     
     temp_max_avg = temp_max_avg/i;
     temp_min_avg = temp_min_avg/i;
     feels_like_avg = feels_like_avg/i;
     effectiveDate = date;
     i=0;
     
     //calcolo della varianza di visibilità
     while(date.equals(effectiveDate)) {
     	i++;
         effectiveDate = "";
         effectiveDate += (city.getTemps().get(i).getData()).charAt(8);
         effectiveDate += (city.getTemps().get(i).getData()).charAt(9);
     	
     }
 
     
     variance /= i;
     
     JSONObject object = new JSONObject();
     JSONObject visibility_data = new JSONObject();
     object.put("CityName", next);
     object.put("Temp_Max Average", temp_max_avg);
     object.put("Temp_Min Average", temp_min_avg);
     object.put("Feels_like Average", feels_like_avg);     
     return object;
 
     
	}

	public JSONObject fiveDayAvg(String next) {
		// TODO Auto-generated method stub
		return null;
	}
	public JSONObject weekAvg(String next) {
		
		return null;
	}
	
	public JSONObject monthAvg(String next) {
		return null;
	}
	
	}	
