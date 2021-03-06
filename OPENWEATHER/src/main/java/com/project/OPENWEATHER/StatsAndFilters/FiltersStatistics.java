package com.project.OPENWEATHER.StatsAndFilters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
import org.json.simple.parser.ParseException;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.Temperature;

public interface  FiltersStatistics {

	public abstract JSONArray Day1Avg(ArrayList<String> cities, String value) throws NotAllowedValueException, MalformedURLException, IOException, ParseException;
	public abstract JSONArray Day5Avg(ArrayList<String> cities, String value) throws NotAllowedValueException, MalformedURLException, IOException, ParseException;

}
