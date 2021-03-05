package com.project.OPENWEATHER.service;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.JSONClass;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.model.URLgeneration;

public class ServiceApplication implements Service {
	
	
	
	
	
	
	// in italiano aggiungere alla fine " &lang=it "
			//unit metric permette di avere le misurazioni in 



	//RestTemplate permette di effettuare la richiesta e di convertire 
	//automaticamente il json ricevuto in un oggetto Java strutturato in maniera 
	//conforme al json atteso; Noi di seguito usiamo questa funzionalità e trasferiamo i dati
	//in un tipo JSONObject che useremo poi in seguito nelle altre classi
	public JSONObject getCityApi(String name) throws MalformedURLException, IOException, ParseException {
		
		
		URLgeneration urlgen = new URLgeneration();

		String url = urlgen.getUrl();
		urlgen.richiesta(name);
		
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
	
	
	public City getTempAPIconverter(String name) {
		JSONArray jj = new JSONArray();
		jj = getTempApi(name);
		return null;
		
		
	}
	
	/**
	 * 
	 * Questo metodo rilegge le informazioni salvate nel periodo selezionato.
	 * Poi salva le informazioni  in un ArrayList di JSONArray e lo passa al metodo per calcolare le varie
	 * statistiche.
	 * 
	 * @param names rappresenta la città o le città su cui si vogliono avere le statistiche.
	 * @param period è il periodo
	 * @throws InvalidStringException se almeno una delle stringhe immesse è vuota.
	 * @throws CitynotFoundException se la città non esiste.
	 * @throws NotAllowedPeriodException se viene inserito un period errato.
	 * @throws IOException se si verifica un errore di lettura del file.
	 * 
	 */
	public ArrayList<JSONArray> HistoryOfTemps(ArrayList<String> names, String period)throws InvalidStringException, NotAllowedPeriodException, CitynotFoundException{
		
		ArrayList<JSONArray> tempinfo = new ArrayList<JSONArray>();
		ArrayList<JSONArray> info = new ArrayList<JSONArray>();
	
		
		//isblank Returns true if the string is empty or contains only white space codepoints,otherwise false.
		for(int j = 0; j<names.size();j++) {
			if(names.get(j).isBlank()) {
				throw new InvalidStringException ("città non valida");
			}
			
			
		}
		return null;
	}

	
	
	
	
	/**
	 *  Questo metodo ci serve per andare a salvare le temperature  
	 * dei 5 giorni successivi e le salva in uno storico.
	 * @param nome della città.
	 * @return file dove vengono salvate le informazioni.
	 */
	
	public String save(String name) throws IOException  {
		
		City city = getTempFutureApi(name);
		JSONObject obj = new JSONObject();
		obj = city.toJSONObject();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
		LocalDateTime now = LocalDateTime.now();	//dtf.format(now)
		
		String path = name+dtf.format(now)+".txt";
		
		
		try {
			PrintWriter write = new PrintWriter(new BufferedWriter( new FileWriter(path)));
			write.println(obj.toString());
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
	
	
	
	
	/**
	 * Questo metodo prende le previsioni meteo future (temperatura
	 * massima, minima, percepita e visibilità).
	 * @param name è il nome della città di cui si vogliono conoscere le previsioni ristrette.
	 * @return un vettore di tipo City che contiene tutte le informazioni richieste e anche le informazioni sulla città.
	 */
	public City getTempFutureApi(String name) {
		City gg = new City();
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		arr = getTempApi(name);
		
		JSONArray wa = obj.getJSONArray("ciao");
		JSONObject cc = new JSONObject();
		
		Vector<Temperature> vec = new Vector<Temperature> (wa.length());
		
		try {
			for(int j=0; j<wa.length();j++){
				Temperature temp = new Temperature();
				cc = wa.getJSONObject(j);
				temp.setTemp(cc.getDouble("temp"));
				temp.setTemp_max(cc.getDouble("temp_max"));
				temp.setTemp_min(cc.getDouble("temp_min"));
				temp.setTemp_avg(cc.getDouble("temp_avg"));
				temp.setFeels_like(cc.getDouble("feels_like"));
				temp.setData(cc.getString("dt_txt"));
				temp.setDescription(cc.getString("description"));
				temp.setMain(cc.getString("main"));
				vec.add(temp);
				}
			}
		catch(Exception e){
			
				e.printStackTrace();
			}
		gg.setTemps(vec);
		return gg;
	}

	
	
	@Override 
	public String FiveDaysInfo(String name) {
		return null;
		}
	
		@Override
		public String URLgenerator(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList<JSONObject> PeriodCity(ArrayList<String> names, String period) throws InvalidStringException,
				NotAllowedPeriodException, InvalidStringException, NotAllowedPeriodException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList<JSONArray> HistoryOfTemps(ArrayList<String> names, int error, String value, int period)
				throws InvalidStringException, NotAllowedPeriodException, CitynotFoundException {
			// TODO Auto-generated method stub
			return null;
		}

		
		
	}
