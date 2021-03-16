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
	private int period;
	
	/**
	 * 
	 * Costruttore della classe
	 * 
	 */
	
	public Filters(ArrayList<String> cities, String param, int period) {
	super();
	this.cities=cities;
	this.param=param;
	this.period=period;
	}
		
	
	/**
	 * Questo metodo fa si che a seconda che si scelga 1 o 5 vengano generate le statistiche 
	 * in un giorno o per 5 giorni
	 * @return
	 * @throws NotAllowedPeriodException se non viene inserito il periodo giusto
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NotAllowedValueException 	  
	 */
	
	public JSONArray analyze() throws NotAllowedPeriodException, NotAllowedParamException, MalformedURLException, IOException, ParseException, NotAllowedValueException {
		
		JSONArray array = new JSONArray ();
	
	
		if(period == 1) {
			
				if(param.equals("temp_max")){
					
					TempMaxAvg filters = new TempMaxAvg();
					array = filters.Day1Avg(cities);

				} 
				else if (param.equals("temp_min")) {
					
					TempMinAvg filters = new TempMinAvg();
					array = filters.Day1Avg(cities);
					
				} 
				else if(param.equals("feels_like")) {
					
					FeelsLikeAvg filters = new FeelsLikeAvg();
					array = filters.Day1Avg(cities);
					
				} 
				else if(param.equals("temp")) {
					
					RealTempAvg filters = new RealTempAvg();
					array = filters.Day1Avg(cities);
				}
				else  {
					
					throw new NotAllowedParamException (param + " non è una stringa ammessa.Inserisci una stringa tra temp_min,temp_max,feels_like e temp");   
				}
			}
			
		else if(period==5) {
					
					if(param.equals("temp_max")){
						
						TempMaxAvg filters = new TempMaxAvg();
						array = filters.Day5Avg(cities);
						
					} 
					else if (param.equals("temp_min")) {
						
						TempMinAvg filters = new TempMinAvg();
						array = filters.Day5Avg(cities);
						
					} 
					else if(param.equals("feels_like")) {
						
						FeelsLikeAvg filters = new FeelsLikeAvg();
						array = filters.Day5Avg(cities);
						
					} 
					else if(param.equals("temp")) {
						
						RealTempAvg filters = new RealTempAvg();
						array = filters.Day5Avg(cities);
					}
					else {
						
						throw new NotAllowedParamException (param + " non è una stringa ammessa.Inserisci una stringa tra temp_min,temp_max,feels_like e temp");
					}
				}
		else {
			
			throw new NotAllowedPeriodException (period + " non è un numero ammesso. Inserisci un numero che sia o 1 o 5.");
		}
		
		return array;
	}
}