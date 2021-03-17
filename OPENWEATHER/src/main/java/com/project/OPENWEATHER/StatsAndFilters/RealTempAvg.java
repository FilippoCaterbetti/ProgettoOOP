package com.project.OPENWEATHER.StatsAndFilters;

import java.io.IOException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class RealTempAvg implements FiltersStatistics {

	Statistics statistic = new Statistics();

	/**
	 * Questo metodo calcola la media delle temperature reali delle città di 1
	 * giorno
	 * 
	 * @param cities contiene le varie città
	 * @return array con temperatura desiderata
	 * @throws ParseException        per errore di parsing
	 * @throws IOException           per errore di input ed output
	 * @throws MalformedURLException per errore di formazione di URL
	 */

	public JSONArray Day1Avg(ArrayList<String> cities) throws MalformedURLException, IOException, ParseException {

		JSONArray array = new JSONArray();

		ArrayList<JSONObject> avg = new ArrayList<JSONObject>();
		ArrayList<Double> avgRealTemp = new ArrayList<Double>();
		ArrayList<JSONObject> objects = new ArrayList<JSONObject>();
		ArrayList<String> names = new ArrayList<String>();

		Iterator<String> it = cities.iterator();

		int i = 0;

		while (it.hasNext()) {
			JSONObject object = new JSONObject();
			object = statistic.todayAvg(it.next());
			avg.add(object);
			double ave = object.getDouble("Real_temp Average");
			avgRealTemp.add(ave);

			JSONObject obj = new JSONObject();
			obj.put("Name:", cities.get(i));
			obj.put("real_temp_avg:", ave);
			objects.add(obj);
			array.put(obj);

			i++;

		}

		return array;

	}

	/**
	 * Questo metodo calcola la media delle temperature reali delle città di 5
	 * giorni
	 * 
	 * @param cities contiene le varie città
	 * @return array con temperatura desiderata
	 * @throws ParseException        per errore di parsing
	 * @throws IOException           per errore di input ed output
	 * @throws MalformedURLException per errore di formazione di URL
	 */

	public JSONArray Day5Avg(ArrayList<String> cities) throws MalformedURLException, IOException, ParseException {
		JSONArray array = new JSONArray();

		ArrayList<JSONObject> average = new ArrayList<JSONObject>();
		ArrayList<Double> averageFeelsLike = new ArrayList<Double>();
		ArrayList<JSONObject> objects = new ArrayList<JSONObject>();
		ArrayList<String> names = new ArrayList<String>();

		Iterator<String> it = cities.iterator();

		int i = 0;

		while (it.hasNext()) {
			JSONObject object = new JSONObject();
			object = statistic.fiveDayAvg(it.next());
			average.add(object);
			double ave = object.getDouble("Real_temp Average");
			averageFeelsLike.add(ave);

			JSONObject obj = new JSONObject();
			obj.put("Name:", cities.get(i));
			obj.put("real_temp_avg:", ave);
			objects.add(obj);
			array.put(obj);

			i++;

		}
		return array;

	}

}