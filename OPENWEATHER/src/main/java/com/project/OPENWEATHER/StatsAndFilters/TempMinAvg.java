package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class TempMinAvg implements FiltersStatistics{

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
* Restituisce la città con la temperatura media minima del primo giorno 
*/
	public City minTempAvgDAY1(Vector<City> toAnalyze) throws InvalidStringException{
		
		if(value.equals("MIN") || value.equals("min") || value.equals("Min")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY1(c.getTemps()) < this.DAY1(giveback.getTemps()))
					giveback = c;
			return giveback;	
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra Min/MIN/min");
	}
/**
* 
* Restituisce la città con la temperatura media minima della settimana 
*/
	public City minTempAvgDAY7(Vector<City> toAnalyze) throws InvalidStringException{
			
		if(value.equals("MIN") || value.equals("min") || value.equals("Min")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY7(c.getTemps()) < this.DAY7(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra Min/MIN/min");
	}
/**
* 
* Restituisce la città con la temperatura media minima del mese 
*/
	public City minTempAvgDAY30(Vector<City> toAnalyze) throws InvalidStringException{
			
		if(value.equals("MIN") || value.equals("min") || value.equals("Min")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY30(c.getTemps()) < this.DAY30(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra Min/MIN/min");
	}
}
