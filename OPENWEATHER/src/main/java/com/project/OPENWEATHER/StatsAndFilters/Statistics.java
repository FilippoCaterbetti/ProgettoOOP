package com.project.OPENWEATHER.StatsAndFilters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.project.OPENWEATHER.error.ErrorCalculator;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.service.ServiceApplication;

public class Statistics extends ErrorCalculator{
	ServiceApplication service = new ServiceApplication();
	 
	public JSONObject todayAvg(String next) throws MalformedURLException, IOException, ParseException{
		
	City city = new City(next);
	 
     city = service.getTempFutureApi(next);
     double temp_max_avg = 0;
     double temp_min_avg = 0;
     double feels_like_avg = 0;
     double real_temp_avg = 0;
     double variance = 0;
     
     int i=0;
     
     String date = "";
     date += (city.getTemps().get(0).getData()).charAt(8);
     date += (city.getTemps().get(0).getData()).charAt(9);
 
     String Date = date;
     while(date.equals(Date)) {
        
    	 temp_max_avg += city.getTemps().get(i).getTemp_max();
         temp_min_avg += city.getTemps().get(i).getTemp_min();
         feels_like_avg += city.getTemps().get(i).getFeels_like();
         real_temp_avg += city.getTemps().get(i).getTemp(); 
         i++;
         Date = "";
         Date += (city.getTemps().get(i).getData()).charAt(0);
         Date += (city.getTemps().get(i).getData()).charAt(1);
     }
     
     temp_max_avg = temp_max_avg/i;
     temp_min_avg = temp_min_avg/i;
     feels_like_avg = feels_like_avg/i;
     real_temp_avg = real_temp_avg/i;
     Date = date;
     i=0;
     
     //calcolo della varianza di visibilità
     while(date.equals(Date)) {
     	i++;
         Date = "";
         Date += (city.getTemps().get(i).getData()).charAt(0);
         Date += (city.getTemps().get(i).getData()).charAt(1);
     	
     }
   //calcolo della varianza della temperatura percepita
     while(i<city.getTemps().size()) {
     	variance += ((int)((city.getTemps().get(i).getTemp())-real_temp_avg))^2;
     	i++;
     }
     
     variance /=i;
     
   //calcolo della varianza della temperatura reale
     while(i<city.getTemps().size()) {
     	variance += ((int)((city.getTemps().get(i).getFeels_like())-feels_like_avg))^2;
     	i++;
     }
     
     variance /=i;     
     JSONObject object = new JSONObject();
     
     object.put("CityName", next);
     object.put("Temp_Max Average", temp_max_avg);
     object.put("Temp_Min Average", temp_min_avg);
     object.put("Feels_like Average", feels_like_avg);  
     object.put("Real_temp Average", real_temp_avg);  
     return object;
 
	}

	public JSONObject fiveDayAvg(String next) throws MalformedURLException, IOException, ParseException {
		City city = new City(next);
		 
	     city = service.getTempFutureApi(next);
	     double temp_max_avg = 0;
	     double temp_min_avg = 0;
	     double feels_like_avg = 0;
	     double real_temp_avg = 0;
	     double variance = 0;
	     
	     int i=0;
	     
	     String date = "";
	     date += (city.getTemps().get(0).getData()).charAt(8);
	     date += (city.getTemps().get(0).getData()).charAt(9);
	 
	     String Date = date;
	     while(date.equals(Date)) {
	        
	    	 temp_max_avg += city.getTemps().get(i).getTemp_max();
	         temp_min_avg += city.getTemps().get(i).getTemp_min();
	         feels_like_avg += city.getTemps().get(i).getFeels_like();
	         real_temp_avg += city.getTemps().get(i).getTemp(); 
	         i++;
	         Date = "";
	         Date += (city.getTemps().get(i).getData()).charAt(0);
	         Date += (city.getTemps().get(i).getData()).charAt(1);
	     }
	     
	     temp_max_avg = temp_max_avg/i;
	     temp_min_avg = temp_min_avg/i;
	     feels_like_avg = feels_like_avg/i;
	     real_temp_avg = real_temp_avg/i;
	     Date = date;
	     i=0;
	     
	     //calcolo della varianza di visibilità
	     while(date.equals(Date)) {
	     	i++;
	         Date = "";
	         Date += (city.getTemps().get(i).getData()).charAt(0);
	         Date += (city.getTemps().get(i).getData()).charAt(1);
	     	
	     }
	   //calcolo della varianza della temperatura percepita
	     while(i<city.getTemps().size()) {
	     	variance += ((int)((city.getTemps().get(i).getTemp())-real_temp_avg))^2;
	     	i++;
	     }
	     
	     variance /=i;
	     
	   //calcolo della varianza della temperatura reale
	     while(i<city.getTemps().size()) {
	     	variance += ((int)((city.getTemps().get(i).getFeels_like())-feels_like_avg))^2;
	     	i++;
	     }
	     
	     variance /=i;     
	     JSONObject object = new JSONObject();
	     
	     object.put("CityName", next);
	     object.put("Temp_Max Average", temp_max_avg);
	     object.put("Temp_Min Average", temp_min_avg);
	     object.put("Feels_like Average", feels_like_avg);  
	     object.put("Real_temp Average", real_temp_avg);  
	     return object;
	 	}
	
	}	
