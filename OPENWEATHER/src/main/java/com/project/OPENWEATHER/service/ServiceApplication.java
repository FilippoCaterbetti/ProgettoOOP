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

	
	
	
	//RestTemplate permette di effettuare la richiesta e di convertire 
	//automaticamente il json ricevuto in un oggetto Java strutturato in maniera 
	//conforme al json atteso; Noi di seguito usiamo questa funzionalità e trasferiamo i dati
	//in un tipo JSONObject che useremo poi in seguito nelle altre classi
	public JSONObject getCityApi(String name) {
		JSONObject obj;
		
		// in italiano aggiungere alla fine " &lang=it "
		//unit metric permette di avere le misurazioni in 
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + name + "&units=metric"+"&appid="+ ApiKey;
	
		
		RestTemplate rest = new RestTemplate();
		obj = new JSONObject(rest.getForObject(url, String.class));
		return obj;
	}
	
	//possimao scegliere, dobbiamo vedere quale è più comodo

	public String URLGenerator(String name, String units)
	{
		String URL = "https://api.openweathermap.org/data/2.5/find?"; //Crea URL 
		URL+= ( name);										  //Concateno i pezzi di stringa e i vari parametri per formare l'URL
		URL+= ("&units=" + units);											
		URL+= ("&appid=" + ApiKey);
		
		return URL;
	}

}*/


	
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
	 * Questo metodo utilizza getCityWeather per andare a prendere le previsioni sulla temperatura della città.
	 * @param name è il nome della città
	 * @return restituisce il JSONArray contente la temperatura reale 
	 * 
	 */
	
	public JSONArray getTempApi(String name) {		
		JSONObject object = getCityApi(name);
		JSONArray toGive = new JSONArray();
		return null;
	}

	
	
	public String save(String name) throws InvalidStringException {
		//da compleetare
		String report = System.get
		
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
		
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();  //ScheduledExecutorService can schedule commands to run after a givendelay, or to execute periodically. 
		scheduler.scheduleAtFixedRate( new Runnable() {
			public void run () {
				JSONArray temp = new JSONArray();
		    	temp = getTempApi(name);
			}
		}
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
