package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;



/**
 * Restituisce la media della temperatura massima del giorno attuale
 * 
 */

	public class TempMaxAvg implements FiltersStatistics{

	Filters v = new Filters();
	String value = v.getValue();
		
	public double DAY1(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}
/**
* Restituisce la media della temperatura massima della settimana
* 
*/
	public double DAY7(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}

/**
* Restituisce la media della temperatura massima del mese
* 
*/
	
	public double DAY30(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}
/**
* 
* Restituisce la città con la temperatura media massima del primo giorno 
 * @throws InvalidStringException genera eccezzione se non si scrive max, MAX, Max
*/
	
	public City maxTempAvgOneday(Vector<City> toAnalyze) throws InvalidStringException{
		
		if(value.equals("max") || value.equals("MAX") || value.equals("Max")) {
			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY1(c.getTemps()) > this.DAY1(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra max/MAX/Max");

	}
/**
* 
* Restituisce la città con la temperatura media massima della settimana 
*/
	public City maxTempAvgWeek(Vector<City> toAnalyze) throws InvalidStringException{
		
		if(value.equals("max") || value.equals("MAX") || value.equals("Max")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY7(c.getTemps()) > this.DAY7(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra max/MAX/Max");
	}
/**
* 
* Restituisce la città con la temperatura media massima del mese 
*/
	public City maxTempAvgMonth(Vector<City> toAnalyze) throws InvalidStringException{
			
		if(value.equals("max") || value.equals("MAX") || value.equals("Max")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY30(c.getTemps()) > this.DAY30(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra max/MAX/Max");
	}
}
