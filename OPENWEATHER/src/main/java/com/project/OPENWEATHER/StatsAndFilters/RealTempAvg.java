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

public class RealTempAvg implements FiltersStatistics{
	
	Statistics statistic = new Statistics();

	/**
	 * 
	 * @param cities
	 * @param value
	 * @return
	 * @throws NotAllowedValueException
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */

	public JSONArray Day1Avg (ArrayList<String> cities, String value) throws NotAllowedValueException, MalformedURLException, IOException, ParseException {

		JSONArray array = new JSONArray();
				
				ArrayList<JSONObject> avg = new ArrayList<JSONObject>();
				ArrayList<Double> avgRealTemp= new ArrayList<Double>();
				ArrayList<JSONObject> objects = new ArrayList<JSONObject>();
				ArrayList<String> names = new ArrayList<String>();
				
				Iterator<String> it = cities.iterator();
				
				double parameter1 = 0;
				double parameter2 = 5000;
			
				int i = 0;
				
				while(it.hasNext()) {
					JSONObject object = new JSONObject();
					object = statistic.todayAvg(it.next());
					avg.add(object);
					double ave = object.getDouble("Temp Avg");
					avgRealTemp.add(ave);
					
					JSONObject obj = new JSONObject();
					obj.put("name:", cities.get(i)); 
					obj.put("temp_avg:",ave);
					objects.add(obj);
					array.put(obj);
					
					if(value.equals("max") || value.equals("MAX") || value.equals("Max")) {
						
							if(ave>parameter1) {
								parameter1 = ave;
								names = new ArrayList<String>();
								names.add(cities.get(i));
							}
							else if(ave==parameter1) {
								names.add(cities.get(i));
							}
							i++;
						
					}
					else if(value.equals("min") || value.equals("MIN") || value.equals("Min")) {
						
						if(ave<parameter2) {
							parameter2 = ave;
							names = new ArrayList<String>();
							names.add(cities.get(i));
						}
						else if(ave==parameter2) {
							
							names.add(cities.get(i));
						}
						i++;
					}
					else throw new NotAllowedValueException (value+" è una stringa errata! Devi inserire una stringa tra max/MAX/Max oppure min/MIN/Min");
						
				}
				
				JSONObject object = new JSONObject();
				
				if(value.equals("max") || value.equals("MAX") || value.equals("Max")) {
					
					object.put("Città con la media più alta", names);
					object.put("media massima", parameter1);
				}
				else { 
					
					object.put("Città con media minima ", names);
					object.put("media minima", parameter2);
				}
				
				
				array.put(object);
				
				
				return array;

			}
	/**
	* 
	* Questo metodo calcola la media delle 
	* temperature percepita delle città in 5 giorno 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	* 
	*/	
			public JSONArray Day5Avg (ArrayList<String> cities, String value) throws NotAllowedValueException, MalformedURLException, IOException, ParseException {
				JSONArray array = new JSONArray();
				
				ArrayList<JSONObject> average = new ArrayList<JSONObject>();
				ArrayList<Double> averageFeelsLike = new ArrayList<Double>();
				ArrayList<JSONObject> objects = new ArrayList<JSONObject>();
				ArrayList<String> names = new ArrayList<String>();
				
				Iterator<String> it = cities.iterator();
				
				double number1 = 0;
				double number2 = 3000;
				
				int i = 0;
				
				while(it.hasNext()) {
					JSONObject object = new JSONObject();
					object = statistic.fiveDayAvg(it.next());
					average.add(object);
					double ave = object.getDouble("Temp Avg");
					averageFeelsLike.add(ave);
					
					JSONObject obj = new JSONObject();
					obj.put("name:",cities.get(i));
					obj.put("temp_avg:",ave);
					objects.add(obj);
					array.put(obj);
					
					if(value.equals("max") || value.equals("MAX") || value.equals("Max")) {
						
						if(ave>number1) {
							
							number1 = ave;
							names = new ArrayList<String>();
							names.add(cities.get(i));
						}
						else if(ave==number1) {
							
							names.add(cities.get(i));
						}
						i++;
						
					}
					else if(value.equals("min") || value.equals("MIN") || value.equals("Min")) {
						
						if(ave<number2) {
							
							number2 = ave;
							
							names = new ArrayList<String>();
							names.add(cities.get(i));
						}
						else if(ave==number2) {
							
							names.add(cities.get(i));
						}
						i++;
					}
					else {
						throw new NotAllowedValueException (value+" è una stringa errata! Devi inserire una stringa tra max/MAX/Max oppure min/MIN/Min");
					}
						
				}
				
				JSONObject object = new JSONObject();
				
				if(value.equals("max") || value.equals("MAX") || value.equals("Max")) {
					
					object.put("Città con la media più alta", names);
					object.put("media massima", number1);
				}
				else { 
					object.put("Città con la media minima", names);
					object.put("media minima", number2);
				}
				
				
				array.put(object);
				
				return array;
				
			}
}