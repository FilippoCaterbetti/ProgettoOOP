package com.project.OPENWEATHER.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class URLgeneration {
	String url;
	String ApiKey;
	
	/**
	 * 
	 * @param name nome della città
	 * @return String url
	 */
	public String url(String name) {
		
		URLgeneration urlgen = new URLgeneration();
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=";
		
		String ApiKey = "";
		urlgen.setApiKey(ApiKey);
		url += ( name );
		url += ( "&units=metric" );
		url += ( "&appid=" + ApiKey);
		urlgen.setUrl(url);
		return url;
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @param name Nome della città 
	 * @return void
	 * @throws IOException Se si verificano problemi durante l'input/output
	 * @throws MalformedURLException Se l'URL per la richiesta di dati all'API di OpenWeather non è corretto
	 * @throws ParseException Se il parsing del body genera eccezioni
	 */
	public  void richiesta(String name) throws MalformedURLException, IOException, ParseException{
		
		JSONParser parser = new JSONParser();
		URLConnection OPENWEATHER = new URL(this.url(name)).openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(OPENWEATHER.getInputStream()));
		
		String inputLine = in.readLine();
		
		JSONObject risposta = (JSONObject) parser.parse(inputLine);

		return;
	}
	
	
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return ApiKey;
	}
	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.ApiKey = apiKey;
	}
	/**
	 * @param url
	 */
	public URLgeneration() {
		super();
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	
}
