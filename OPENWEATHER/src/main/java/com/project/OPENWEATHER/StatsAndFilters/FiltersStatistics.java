package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.Temperature;

public interface  FiltersStatistics {

	public abstract double DAY1(Vector <Temperature> dati) throws InvalidStringException;
	public abstract double DAY7(Vector <Temperature> dati) throws InvalidStringException;
	public abstract double DAY30(Vector <Temperature> dati) throws InvalidStringException;

}
