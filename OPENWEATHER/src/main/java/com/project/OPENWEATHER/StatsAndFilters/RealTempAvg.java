package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class RealTempAvg implements FiltersStatistics{
	

	Filters v = new Filters();
	String value = v.getValue();
	
	public double DAY1(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}

	public double DAY7(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}

	public double DAY30(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura effettiva del primo giorno 
*/
	public City maxRealTempOneday(Vector<City> toAnalyze) throws InvalidStringException{
		
		if(value.equals("Avg") || value.equals("AVG") || value.equals("avg") || value.equals("average")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY1(c.getTemps()) > this.DAY1(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra avg/AVG/Avg/average");
	}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura media effettiva della settimana
*/
	public City maxRealTempWeek(Vector<City> toAnalyze) throws InvalidStringException{
		
		if(value.equals("Avg") || value.equals("AVG") || value.equals("avg") || value.equals("average")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY7(c.getTemps()) > this.DAY7(giveback.getTemps()))
					giveback = c;
			return giveback;
			}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra avg/AVG/Avg/average");
		}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura media effettiva del mese
*/
	public City maxRealTempMonth(Vector<City> toAnalyze) throws InvalidStringException{
			
		if(value.equals("Avg") || value.equals("AVG") || value.equals("avg") || value.equals("average")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)					
				if(this.DAY30(c.getTemps()) > this.DAY30(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra avg/AVG/Avg/average");
	}
}
