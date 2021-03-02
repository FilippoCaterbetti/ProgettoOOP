package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class RealTempAvg implements FiltersStatistics{

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
	public City maxRealTempDAY1(Vector<City> toAnalyze){
					
			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY1(c.getTemps()) > this.DAY1(giveback.getTemps()))
					giveback = c;
			return giveback;
		}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura effettiva della settimana
*/
	public City maxRealTempDAY7(Vector<City> toAnalyze){
						
			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)
				if(this.DAY7(c.getTemps()) > this.DAY7(giveback.getTemps()))
					giveback = c;
			return giveback;
		}
/**
* 
* Restituisce la città con il massimo dislivello di temperatura effettiva del mese
*/
	public City maxRealTempDAY30(Vector<City> toAnalyze){
							
			City giveback = toAnalyze.elementAt(0);
			for(City c : toAnalyze)					
				if(this.DAY30(c.getTemps()) > this.DAY30(giveback.getTemps()))
					giveback = c;
			return giveback;
		}

}
