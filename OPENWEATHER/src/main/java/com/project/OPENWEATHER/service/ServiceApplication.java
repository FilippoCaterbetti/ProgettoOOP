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

import com.project.OPENWEATHER.error.ErrorCalculator;
import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.EmptyStringException;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.exception.WrongPeriodException;
import com.project.OPENWEATHER.exception.WrongValueException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.JSONClass;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.model.URLgeneration;

public class ServiceApplication implements Service {
	

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
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * 
	 */
	//CONTROLARE PARSEEXCEPTION
	public JSONArray getTempApi(String name) throws MalformedURLException, IOException, ParseException {		
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
		String report;
		report = System.getProperty("user.dir")+"/"+name+"report.txt";
		File file = new File(name+"Report.txt");
    
		
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		    	
		    	JSONArray tempApi = new JSONArray();
		    	try {
					tempApi = getTempApi(name);
				} catch (MalformedURLException e1) {
					
					e1.printStackTrace();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				} catch (ParseException e1) {
					
					e1.printStackTrace();
				}
		    	
		    	JSONObject oggetto = new JSONObject(); 
		    	oggetto = tempApi.getJSONObject(0);
		    	
		    	for(int i=0;i<oggetto.length();i++ ) {
		    			    	
		    			try{
		    				
		    			    if(!file.exists()) {
		    			        file.createNewFile();
		    			    }

		    			    FileWriter fileWriter = new FileWriter(file, true);
		    				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		    			    bufferedWriter.write(oggetto.toString());
		    			    bufferedWriter.write("\n");
		    			    
		    			    bufferedWriter.close();
		    			    
		    			} catch(IOException e) {
		    			    System.out.println(e);
		    			}
		    		}
		    }
		}, 0, 5, TimeUnit.HOURS); 
		
		return "I dati sono stati inseriti nel file txt "+report;
		 
	}	
	
	/**
	 * Questo metodo viene richiamato da readHistoryError e da readVisibilityHistory.
	 * Si occupa della lettura dello storico della città passata in ingresso. A seconda che il flag sia true o false, il 
	 * metodo andrà a leggere lo storico per il calcolo della soglia di errore e delle previsioni azzeccate oppure per 
	 * le statistiche sulla visibilità.
	 * 
	 * @param name è il nome della città di cui si vuole leggere lo storico.
	 * @param flag indica quale storico andare a leggere.
	 * @return il JSONArray che contiene tutte le informazioni sulla visibilità.
	 * @throws IOException se si verificano errori di input da file.
	 */
	
	public JSONArray readHistory(String name, boolean flag) throws IOException {
		
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
	 * Questo metodo serve per raccogliere le informazioni dallo storico di ogni città passata in ingresso 
	 * e richiama altri metodi che servono per leggere lo storico stesso e metodi per calcolare statistiche e filtrarle.
	 * 
	 * @param names contiene i nomi di tutte le città su cui si vogliono fare statistiche sulla soglia di
	 *        errore e applicare i filtri.
	 * @param error è l'intero che rappresenta la soglia con cui si vuole filtrare.
	 * @param value esprime il filtro che si vuole applicare, cioè se si vuole sapere quali città hanno un errore maggiore
	 *        o minore dell'intero error che è stato inserito. Le stringhe ammesse sono: "$lt", "$gt" e "=".
	 * @param period rappresenta i giorni di predizione (da 1 a 5 inclusi).
	 * @return restituisce l'ArrayList di JSONObject filtrati secondo i filtri indicati.
	 * @throws EmptyStringException se almeno uno dei nomi inseriti è uguale alla stringa vuota.
	 * @throws CityNotFoundException se l'utente ha inserito una città di cui non è presente lo storico. Le stringhe ammesse
	 *         sono: "Ancona","Milano","Roma","Bologna", "Parigi".
	 * @throws WrongPeriodException se l'utente ha inserito un numero che non è compreso tra 1 e 5 (inclusi).
	 * @throws WrongValueException se l'utente ha inserito una stringa non ammessa per il value.
	 * @throws IOException se si verificano problemi nella lettura del file.
	 */
	public ArrayList<JSONObject> readHistoryError(ArrayList<String> names ,int error, String value,int period) throws EmptyStringException, CitynotFoundException, NotAllowedPeriodException, NotAllowedValueException, IOException {
		
		for(int i=0; i < names.size(); i++) {
			if(names.get(i).isEmpty())
				throw new EmptyStringException("Hai dimenticato di inserire la città");
			else if(!(names.get(i).equals("Ancona") || names.get(i).equals("Milano") || names.get(i).equals("Roma") || names.get(i).equals("Bologna") || names.get(i).equals("Parigi")))
				throw new CitynotFoundException("La città inserita non è presente nello storico");
			
		if(period < 1 || period > 5)
				throw new NotAllowedPeriodException(period + " Il periodo inserito non è valido. Inserisci un numero compreso tra 1 e 5 inclusi.");
		}
		
		if(!(value.equals("$gt") || value.equals("$lt") || value.equals("=")))
			throw new NotAllowedValueException(value + " La stringa immessa non è consentita. Devi inserire una stringa tra \"$gt\", \"$lt\" e \"=\"");
		
		Iterator<String> list = names.iterator();
		
		ArrayList<JSONArray> statisticArray = new ArrayList<JSONArray>(); 
		ArrayList<JSONObject> errors = new ArrayList<JSONObject>();
		
		while(list.hasNext()) {
			
			JSONArray array = new JSONArray();
			array = readHistory(list.next(),true);
			JSONArray visibilityInfo = new JSONArray();
			
			for(int i=0; i<array.length(); i++) {
				
				JSONArray visibilityday = new JSONArray();
				
				JSONObject weather = new JSONObject();
				weather = array.getJSONObject(i);
				
				JSONArray arr = new JSONArray();
				arr = weather.getJSONArray("Weather");
				
				
				for(int j=0; j<arr.length();j++) {
					
					JSONObject visibility = new JSONObject();
					JSONObject all = new JSONObject();
					all = arr.getJSONObject(j);
					
					visibility.put("visibility", all.get("visibility"));
					visibility.put("data", all.get("data"));
					visibilityday.put(visibility);
					
				}
				
				visibilityInfo.put(visibilityday);
				
			}
			
			statisticArray.add(visibilityInfo);
		}
		
		ErrorCalculator errorcalculator = new ErrorCalculator();
		errors = errorcalculator.calculate(names,tempInfo, error, value, period);
		
		return errors;
	}
	
	
	
	/**
	 * Questo metodo prende le previsioni meteo future (temperatura
	 * massima, minima, percepita).
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
