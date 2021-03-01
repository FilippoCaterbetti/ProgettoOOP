package com.project.OPENWEATHER.service;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.JSONClass;

public abstract class ServiceApplication implements Service {
	
	 //da completare
	private String ApiKey = ""; //mettere alla fine 
	
	
	
	// in italiano aggiungere alla fine " &lang=it "
			//unit metric permette di avere le misurazioni in 

	//RestTemplate permette di effettuare la richiesta e di convertire 
	//automaticamente il json ricevuto in un oggetto Java strutturato in maniera 
	//conforme al json atteso; Noi di seguito usiamo questa funzionalità e trasferiamo i dati
	//in un tipo JSONObject che useremo poi in seguito nelle altre classi
	public JSONObject getCityApi(String name) {
		
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=";
		url += ( name );
		url += ( "&units=metric" );
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
	 * Questo metodo utilizza getCityWeather per andare a prendere i dati sulla temperatura della città.
	 * @param name è il nome della città
	 * @return restituisce il JSONArray contente la temperatura reale 
	 * 
	 */
	
	public JSONArray getTempApi(String name) {		
		JSONObject object = getCityApi(name);
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
			description= sp.get("description").toString();
			
			JSONObject g = new JSONObject();
			g.put("temp", temp);
			g.put("temp_max", temp_max);
			g.put("temp_min", temp_min);
			g.put("temp_avg", temp_avg);
			g.put("feels_like", feels_like);
			g.put("Data", data);
			g.put("main", main);
			g.put("description", description);
			tmp.put(g);
			
		}
		return tmp;
	}

	/** Questo metodo ci serve per andare a salvare le informazioni 
	 * dei prossimi cinque giorni delle città e le salva in uno storico.
	 * @param nome della città.
	 * @return un oggetto di tipo città popolato delle informazioni sulla città.
	 */
	
	public String save(String name) throws IOException  {
		
		JSONArray city = getTempFutureApi(name);
		JSONObject obj = new JSONObject();
		obj = city.toJSONObject(city);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
		LocalDateTime now = LocalDateTime.now();	//dtf.format(now)
		
		String path = name+dtf.format(now)+".txt";
		
		
		try {
			PrintWriter write = new PrintWriter(new BufferedWriter( new FileWriter(path)));
			write.println(obj);
			write.println();
			write.close();
		}
		catch(IOException e){
			System.out.println("ERRORE di I/O"); 
			System.out.println(e);
		}
		
		return path;
	}
	
	/**
	 * 
	 * Questo metodo richiama x e salva le previsioni ogni 5 ore.
	 * @param name è il nome della città
	 * @return una stringa contenente il path del file salvato.
	 * 
	 */
	public String FiveHoursInfo(String name) {
		//String report = System.getProperty("user.dir") + "/" + name + "HourlyReport.txt";
		//File file = new File(report);
		
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		    	
		    	File file = new File(name+"Report.txt");
		    	JSONArray temps = new JSONArray();
		    	temps = getTempApi(name);
		    	JSONObject temps2 = new JSONObject();
		    	
		    	for(int i=0;i<temps.length();i++ ) {
		    	
		    		temps2= temps.getJSONObject(i);
		    	
		    			try{
		    				
		    			    if(!file.exists()) {
		    			        file.createNewFile();
		    			    }

		    			    FileWriter fileWriter = new FileWriter(file, true);
		    				
		    				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		    			    bufferedWriter.write(temps2.toString());
		    			    bufferedWriter.write("\n");
		    			    
		    			    bufferedWriter.close();
		    			    
		    			} catch(IOException e) {
		    			    System.out.println(e);
		    			}
		    		}
		    }
		}, 0, 5, TimeUnit.HOURS); 
		
		return "I dati sono stati inseriti";
		 
	}	
	
	//161finire
	public JSONArray getTempFutureApi(String name) {
		
		JSONObject obj = new JSONObject();
		
		
		return null;
		
	}

	
	@Override 
	public ArrayList<JSONObject> PeriodCity (String name,  String period){
			return null ;
		}
	
	@Override 
	public String FiveDaysInfo(String name) {
		return null;
		}
	}
