package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class Filters {    
	
	private ArrayList<String> cities = new ArrayList<String>();
	private String param;
	private String value;
	private String period;
	
	/**
	 * 
	 * Costruttore della classe
	 * 
	 */
	public Filters(ArrayList<String> cities, String param, String value, String period) {
	super();
	this.cities=cities;
	this.param=param;
	this.value=value;
	this.period=period;
	}


	
	public Filters(ArrayList<String> cities, String param,  String period) {
		this.cities = cities;
		this.param = param;
		this.period = period;
	}

	
	
	
	public Filters() {
	}



	/**
	 * 
	 * @return
	 * @throws NotAllowedPeriodException se non viene inserito il periodo giusto
	 * @throws WrongMethodTypeException 
	 * 
	 */
	
	public JSONArray analyze() throws NotAllowedPeriodException, InvalidStringException {
		
		JSONArray array = new JSONArray ();
		Vector <Temperature> dati= new Vector <Temperature>();
		City c = new City(); 
		dati = c.getTemps();
		
			if(period=="giornaliero") {
				
				if(param.equals("temp_max")){
					
					TempMaxAvg filters = new TempMaxAvg();
					double x = filters.DAY1(dati);
					
				} else if (param.equals("temp_min")) {
					
					TempMinAvg filters = new TempMinAvg();
					double x = filters.DAY1(dati);
					
				} else if(param.equals("feels_like")) {
					
					FeelsLikeAvg filters = new FeelsLikeAvg();
					double x = filters.DAY1(dati);
					
				} else if(param.equals("temp")) {
					
					RealTempAvg filters = new RealTempAvg();
					double x = filters.DAY1(dati);
				}
	
			}
			
		if(period=="settimanale") {
					
					if(param.equals("temp_max")){
						
						TempMaxAvg filters = new TempMaxAvg();
						double x = filters.DAY7(dati);
						
					} else if (param.equals("temp_min")) {
						
						TempMinAvg filters = new TempMinAvg();
						double x = filters.DAY7(dati);
						
					} else if(param.equals("feels_like")) {
						
						FeelsLikeAvg filters = new FeelsLikeAvg();
						double x = filters.DAY7(dati);
						
					} else if(param.equals("temp")) {
						
						RealTempAvg filters = new RealTempAvg();
						double x = filters.DAY7(dati);
					}
		
				}
		if(period=="mensile") {
			
			if(param.equals("temp_max")){
				
				TempMaxAvg filters = new TempMaxAvg();
				double x = filters.DAY30(dati);
				
			} 
			else if (param.equals("temp_min")) {
				
				TempMinAvg filters = new TempMinAvg();
				double x = filters.DAY30(dati);
				
			} 
			else if(param.equals("feels_like")) {
				
				FeelsLikeAvg filters = new FeelsLikeAvg();
				double x = filters.DAY30(dati);
				
			} 
			else if(param.equals("temp")) {
				
				RealTempAvg filters = new RealTempAvg();
				double x = filters.DAY30(dati);
			}
		} else 
			throw new NotAllowedPeriodException (period+"Inserisci un valore che sia giornaliero, settimanale o mensile");
	
		
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