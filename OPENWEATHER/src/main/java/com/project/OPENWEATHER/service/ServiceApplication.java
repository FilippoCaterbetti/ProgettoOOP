package com.project.OPENWEATHER.service;
import java.io.File;


import java.lang.Exception;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;

public class ServiceApplication implements Service {
	
	 //da completare
	private String ApiKey = ""; //mettere alla fine 
	
	
	
	// in italiano aggiungere alla fine " &lang=it "
			//unit metric permette di avere le misurazioni in 

	
	


	//RestTemplate permette di effettuare la richiesta e di convertire 
	//automaticamente il json ricevuto in un oggetto Java strutturato in maniera 
	//conforme al json atteso; Noi di seguito usiamo questa funzionalità e trasferiamo i dati
	//in un tipo JSONObject che useremo poi in seguito nelle altre classi
	public JSONObject getCityApi(String name, int cnt) {
		
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=";
		url += ( name );
		url += ( "&units=metric" );
		url += ( "&cnt=" + cnt);
		url += ( "&appid=" + ApiKey);
		
		JSONObject obj;
		RestTemplate rest = new RestTemplate();
		obj = new JSONObject(rest.getForObject(url, String.class));
		return obj;
	}
	
	
	/**
	 * 
	 * Genera l'URL per chimare l'API di Openwheather Cities in circle
	 * 
	 * @param name è nome della città
	 * @param lon Longitudine
	 * @param cnt Numero di città da cercare
	 * @param units Stringa che definisce il tipo di unità con le quali l'API di Openweather deve fornire i dati
	 * @return URL sotto forma di stringa per chiamare l'API di OpenWeather
	 * 
	 */
	
	
	//previsioni temperatura città scelta 
	/*public JSONArray getTempApi( String name) {
		JSONObject obj = getCityApi(name);
		JSONArray mainTemp = new JSONArray();
		double temp_max;
		double temp_min;
		double temp_avg;
		double feels_like;
		double temp;
		String data;
		
		
	}*/
	
	
	
	
	
	/**
	 * 
	 * Questo metodo utilizza getCityWeather per andare a prendere le temperature sulla temperatura della città.
	 * @param name è il nome della città
	 * @return restituisce il JSONArray contente la temperatura reale 
	 * 
	 */
	
	public JSONArray getTempApi(String name, int cnt) {		
		JSONObject object = getCityApi(name, cnt);
		JSONArray tmp = new JSONArray();
		
		JSONArray TempArray = object.getJSONArray("list");
		JSONObject sp;
		double temp;
		double temp_max;
		double temp_min;
		double temp_avg;
		double feels_like;
		String data;
		String main;
		String description;
		
		for (int i = 0; i<TempArray.length(); i++) {
			
			sp = TempArray.getJSONObject(i);
			temp = Double.parseDouble(sp.get("temp").toString());
			temp_max = Double.parseDouble(sp.get("temp_max").toString());
			temp_min = Double.parseDouble(sp.get("temp_min").toString());
			temp_avg = Double.parseDouble(sp.get("temp_avg").toString());
			feels_like = Double.parseDouble(sp.get("feels_like").toString());
			data =  sp.get("dt_txt").toString(); //?
			main = sp.get("main").toString();
			description =  sp.get("description").toString();
			
			JSONObject g = new JSONObject();
			g.put("temp", temp);
			g.put("temp_max", temp_max);
			g.put("temp_min", temp_min);
			g.put("temp_avg", temp_avg);
			g.put("feels_like", feels_like);
			g.put("description", description);
			g.put("main", main);
			g.put("Data", data);
			tmp.put(g);
			
		}
		return tmp;
	}

	
	
	public String save(String name) throws InvalidStringException {
		
		//String report = System.get
		
		return null;
	}

	/**
	 * 
	 * Questo metodo richiama x e salva le previsioni ogni 5 ore.
	 * @param name è il nome della città
	 * @return una stringa contenente il path del file salvato.
	 * 
	 */
	public String FiveHoursInfo(String name) {
		String report = System.getProperty("user.dir") + "/" + name + "FiveHoursInfo.txt";
		File file = new File(report);
		
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();  //ScheduledExecutorService can  schedule commands to
		scheduler.scheduleAtFixedRate( new Runnable() {										// run after a givendelay, or to execute periodically.
			public void run () {
				@Override
				JSONArray temp = new JSONArray();
		    	temp = getTempApi(name);
			}
		}, 0, 0, null);
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
