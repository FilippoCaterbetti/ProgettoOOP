package com.project.OPENWEATHER.service;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.EmptyStringException;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.City;

public interface Service {

	public abstract JSONObject getCityApi(String name);

	public abstract JSONArray getTempApi(String name);

	public String FiveHoursInfo(String name);

	public City getCityInfofromApi(String name);

	public ArrayList<JSONObject> HistoryOfError(ArrayList<String> names, int error, String value, int period)
			throws EmptyStringException, CitynotFoundException, NotAllowedPeriodException, NotAllowedValueException,
			IOException, InvalidStringException;

	public abstract City getTempFutureApi(String name);

	public JSONArray Substring(String regex) throws FileNotFoundException, IOException, ParseException;

	public ArrayList<JSONArray> PeriodHistory(ArrayList<String> cities, String period) throws EmptyStringException,
			CitynotFoundException, NotAllowedPeriodException, IOException, InvalidStringException;

	public String save(String name) throws IOException, ParseException;

	public JSONObject readHistory(String name) throws IOException, InvalidStringException;

	public JSONArray listOfCities();

}
