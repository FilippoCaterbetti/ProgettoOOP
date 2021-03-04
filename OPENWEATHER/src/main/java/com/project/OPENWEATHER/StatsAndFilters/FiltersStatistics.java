package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.Temperature;

public interface  FiltersStatistics {

	public abstract double Day1(Vector <Temperature> dati) throws InvalidStringException;
	public abstract double Day7(Vector <Temperature> dati) throws InvalidStringException;
	public abstract double Day30(Vector <Temperature> dati) throws InvalidStringException;

}
