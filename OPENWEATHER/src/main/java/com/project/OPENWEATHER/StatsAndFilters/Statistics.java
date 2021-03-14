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
	 
	/**
	 * @param next è il nome della città che viene passata della quale si vuole sapere la media delle statistiche raccolte
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public JSONObject todayAvg(String next) throws MalformedURLException, IOException, ParseException{
		
	City city = new City(next);
	 
     city = service.getTempFutureApi(next);
     
     double temp_max_avg = 0;
     double temp_min_avg = 0;
     double feels_like_avg = 0;
     double real_temp_avg = 0;
     
     double variancefeelslike = 0;
     double variancetemp= 0;

     
     int i=0;
     
     String date = "";
     date += (city.getTemps().get(0).getData()).charAt(8);
     date += (city.getTemps().get(0).getData()).charAt(9);
 
     String datas = date;
     
     double max_temp = 0;
     double min_temp = city.getTemps().get(i).getTemp();
     double max_feelslike = 0;
     double min_feelslike = city.getTemps().get(i).getFeels_like();
     
     while(date.equals(datas)) {
        
    	 temp_max_avg += city.getTemps().get(i).getTemp_max();
         temp_min_avg += city.getTemps().get(i).getTemp_min();
         feels_like_avg += city.getTemps().get(i).getFeels_like();
         real_temp_avg += city.getTemps().get(i).getTemp(); 
         if(city.getTemps().get(i).getTemp()>max_temp) {
        	 
        	 max_temp = city.getTemps().get(i).getTemp();

         }
         if (city.getTemps().get(i).getTemp()<min_temp) {
        	 
        	 min_temp = city.getTemps().get(i).getTemp();

         }
         if(city.getTemps().get(i).getFeels_like()>max_feelslike) {
        	 
        	 max_feelslike = city.getTemps().get(i).getFeels_like();

         }
         if (city.getTemps().get(i).getFeels_like()<min_feelslike) {
        	 
        	 min_feelslike = city.getTemps().get(i).getFeels_like();

         }
         i++;
         datas = "";
         datas += (city.getTemps().get(i).getData()).charAt(8);
         datas += (city.getTemps().get(i).getData()).charAt(9);
     }
     
     temp_max_avg = temp_max_avg/i;
     temp_min_avg = temp_min_avg/i;
     feels_like_avg = feels_like_avg/i;
     real_temp_avg = real_temp_avg/i;
     datas = date;
     i=0;
     
     //calcolo della varianza di visibilità
     while(date.equals(datas)) {
    	 variancetemp = ((int)((city.getTemps().get(i).getTemp())-real_temp_avg))^2;
    	 variancefeelslike = ((int)((city.getTemps().get(i).getFeels_like())-feels_like_avg))^2;

    	 i++;
     	datas = "";
     	datas += (city.getTemps().get(i).getData()).charAt(8);
     	datas += (city.getTemps().get(i).getData()).charAt(9);
     	
     }
   
     
     variancetemp /=i; 
     variancefeelslike /= i;
     
     
     JSONObject object = new JSONObject();
     JSONObject temp_data = new JSONObject();
     
     
     temp_data.put("Real temp average",real_temp_avg);
     temp_data.put("Max Real temp",max_temp);
     temp_data.put("Min Real temp", min_temp);
     temp_data.put("Real temp variance", variancetemp);
     
     temp_data.put("Feels like average",feels_like_avg);
     temp_data.put("Max feels like",max_feelslike);
     temp_data.put("Min feels like", min_feelslike);
     temp_data.put("Feels like variance", variancefeelslike);
     
     
     object.put("City", next);
     object.put("Temp_Max Average", temp_max_avg);
     object.put("Temp_Min Average", temp_min_avg);
     object.put("Temp and Feels like data", temp_data);
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
	 
	     String datas = date;
	     while(date.equals(datas)) {
	        
	    	 temp_max_avg += city.getTemps().get(i).getTemp_max();
	         temp_min_avg += city.getTemps().get(i).getTemp_min();
	         feels_like_avg += city.getTemps().get(i).getFeels_like();
	         real_temp_avg += city.getTemps().get(i).getTemp(); 
	         i++;
	         datas = "";
	         datas += (city.getTemps().get(i).getData()).charAt(8);
	         datas += (city.getTemps().get(i).getData()).charAt(9);
	     }
	     
	     temp_max_avg = temp_max_avg/i;
	     temp_min_avg = temp_min_avg/i;
	     feels_like_avg = feels_like_avg/i;
	     real_temp_avg = real_temp_avg/i;
	     datas = date;
	     i=0;
	     
	     //calcolo della varianza di visibilità
	     while(date.equals(datas)) {
	     	i++;
	     	datas = "";
	     	datas += (city.getTemps().get(i).getData()).charAt(8);
	     	datas += (city.getTemps().get(i).getData()).charAt(9);
	     	
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
