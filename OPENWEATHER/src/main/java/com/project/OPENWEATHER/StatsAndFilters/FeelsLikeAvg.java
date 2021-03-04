package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class FeelsLikeAvg implements FiltersStatistics{
	
	Filters v = new Filters();
	String value = v.getValue();
	
	public double Day1(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}

	public double Day7(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}

	public double Day30(Vector <Temperature> dati) {
		
		double sommaTemp=0;
		for(Temperature t : dati)
			sommaTemp += t.getTemp();
		return (sommaTemp/dati.size());
	}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura percepita del primo giorno 
*/
	public City maxFeelsLikeOneday(Vector<City> toAnalyze) throws InvalidStringException{
			
		if(value.equals("FeelsLike") || value.equals("Fl") || value.equals("percepita")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.Day1(c.getTemps()) > this.Day1(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra FeelLike/Fl/percepita");
	}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura percepita della settimana
*/
	public City maxFeelsLikeWeek(Vector<City> toAnalyze) throws InvalidStringException{
			
		if(value.equals("FeelsLike") || value.equals("Fl") || value.equals("percepita")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.Day7(c.getTemps()) > this.Day7(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra FeelLike/Fl/percepita");
	}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura percepita del mese
*/
	public City maxFeelsLikeMonth(Vector<City> toAnalyze) throws InvalidStringException{
			
		if(value.equals("FeelsLike") || value.equals("Fl") || value.equals("percepita")) {

			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.Day30(c.getTemps()) > this.Day30(giveback.getTemps()))
					giveback = c;
			return giveback;
		}	else throw new InvalidStringException (value+" è una stringa errata! Devi inserire una stringa tra FeelLike/Fl/percepita");
	}
}
