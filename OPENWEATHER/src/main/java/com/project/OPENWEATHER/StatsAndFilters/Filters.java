package com.project.OPENWEATHER.StatsAndFilters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.simple.parser.ParseException;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedParamException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class Filters {    
	
	private ArrayList<String> cities = new ArrayList<String>();
	private String param;
	private String value;
	private int period;
	
	/**
	 * 
	 * Costruttore della classe
	 * 
	 */
	public Filters(ArrayList<String> cities, String param, String value, int period) {
	super();
	this.cities=cities;
	this.param=param;
	this.value=value;
	this.period=period;
	}

	public Filters(ArrayList<String> cities, int period) {
		super();
		this.cities=cities;
		this.period=period;
	}
	
	public Filters(ArrayList<String> cities, String param, int period) {
		super();
		this.cities = cities;
		this.param = param;
		this.period = period;
	}

	
	
	
	/**
	 * 
	 * @return
	 * @throws NotAllowedPeriodException se non viene inserito il periodo giusto
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NotAllowedValueException 
	 * @throws WrongMethodTypeException 
	 * 
	 */
	
	public JSONArray analyze() throws NotAllowedPeriodException, NotAllowedParamException, MalformedURLException, IOException, ParseException, NotAllowedValueException {
		
		JSONArray array = new JSONArray ();
	
	
			if(period == 1) {
				
				if(param.equals("temp_max")){
					
					TempMaxAvg filters = new TempMaxAvg();
					array = filters.Day1Avg(cities, value);

				} else if (param.equals("temp_min")) {
					
					TempMinAvg filters = new TempMinAvg();
					array = filters.Day1Avg(cities, value);
					
				} else if(param.equals("feels_like")) {
					
					FeelsLikeAvg filters = new FeelsLikeAvg();
					array = filters.Day1Avg(cities, value);
					
				} else if(param.equals("temp")) {
					
					RealTempAvg filters = new RealTempAvg();
					array = filters.Day1Avg(cities, value);
				}
				else  throw new NotAllowedParamException (param + " non è una stringa ammessa.Inserisci una stringa tra temp_min,temp_max,feels_like e temp");   
			}
			
		if(period==5) {
					
					if(param.equals("temp_max")){
						
						TempMaxAvg filters = new TempMaxAvg();
						array = filters.Day5Avg(cities, value);
						
					} else if (param.equals("temp_min")) {
						
						TempMinAvg filters = new TempMinAvg();
						array = filters.Day5Avg(cities, value);
						
					} else if(param.equals("feels_like")) {
						
						FeelsLikeAvg filters = new FeelsLikeAvg();
						array = filters.Day5Avg(cities, value);
						
					} else if(param.equals("temp")) {
						
						RealTempAvg filters = new RealTempAvg();
						array = filters.Day5Avg(cities,value);
					}
					else  throw new NotAllowedParamException (param + " non è una stringa ammessa.Inserisci una stringa tra temp_min,temp_max,feels_like e temp");
				}
		else  throw new NotAllowedPeriodException (period + " non è un numero ammesso. Inserisci un numero che sia o 1 o 5.");
		return array;
	}
	
	


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}



	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}