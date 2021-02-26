package com.project.OPENWEATHER.service;
import java.lang.Exception;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;

public class ServiceApplication implements Service {
	
	 //da completare
	private String ApiKey = ""; //mettere alla fine DATO SENSIBILE

	
	
	
	//RestTemplate permette di effettuare la richiesta e di convertire 
	//automaticamente il json ricevuto in un oggetto Java strutturato in maniera 
	//conforme al json atteso; Noi di seguito usiamo questa funzionalità e trasferiamo i dati
	//in un tipo JSONObject che useremo poi in seguito nelle altre classi
	public JSONObject getCityApi(String name) {
		JSONObject obj;
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=" +name+ "&appid="+ ApiKey;
		
		RestTemplate rest = new RestTemplate();
		obj = new JSONObject(rest.getForObject(url, String.class));
		return obj;
	}

	//ServiceImpl.java 72
	
	//previsioni temperatura città scelta 
	public JSONArray getTempApi( String name) {
		JSONObject obj = getCityApi(name);
		JSONArray mainTemp = new JSONArray();
		double temp_max;
		double temp_min;
		double temp_avg;
		double feels_like;
		double temp;
		String data;
		
		
	}
	
	
	
	
	
	
	
	public City getTempApi(String name) {		
		
		return null;
	}

	@Override
	public String save(String name) throws InvalidStringException {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public String FiveHourInfo(String name) {
		// TODO Auto-generated method stub
		
		
		return null;
	}
	
	@Override 
	public ArrayList<JSONObject> PeriodCity (String name,  String period ){
			return ;
		}
	
	@Override 
	public String FiveDaysInfo(String name) {
		return null;
		}
	}
