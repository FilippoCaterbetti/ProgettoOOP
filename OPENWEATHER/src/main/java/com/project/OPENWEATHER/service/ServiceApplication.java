package com.project.OPENWEATHER.service;
import java.io.BufferedReader;


import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.OPENWEATHER.StatsAndFilters.PeriodStatistics;
import com.project.OPENWEATHER.error.ErrorCalculator;
import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.EmptyStringException;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.service.JSONClass;
import com.project.OPENWEATHER.model.Temperature;


public class ServiceApplication implements com.project.OPENWEATHER.service.Service {
	
	private String Api_key = "069782b0f7fc9729e6c7151ffd1448ed";

	
	/**
	 * previsioni meteo di una città.
	 * @param nome città 
	 * @return un JSONObject con le previsioni meteo.
	 */
	//RestTemplate permette di effettuare la richiesta e di convertire 
	//automaticamente il json ricevuto in un oggetto Java strutturato in maniera 
	//conforme al json atteso; Noi di seguito usiamo questa funzionalità e trasferiamo i dati
	//in un tipo JSONObject che useremo poi in seguito nelle altre classi
	public JSONObject getCityApi(String name){
		
		
		JSONObject obj;
		
		/**
		 * Questo serve per avere le temperature in gradi centigradi
		 * Per la temperatura in Fahrenheit utilizzare &units=imperial
		 * La temperatura in Kelvin viene utilizzata per impostazione predefinita, non è necessario utilizzare il parametro delle unità nella chiamata API
		 * quindi basta cancellare il parametro
		 */
		String unit = "&units=metric";
		
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + name + unit + "&appid="+Api_key;
		
		RestTemplate rt = new RestTemplate();
		
		obj = new JSONObject(rt.getForObject(url, String.class));
		
		return obj;

	}

	
	/**
	 * 
	 * Questo metodo utilizza getCityWeather per andare a prendere i dati sulle varie temperature della città.
	 * 
	 * @param name è il nome della città
	 * @return restituisce il JSONArray contente la temperatura reale 
	 * 
	 */

	public JSONArray getTempApi(String name) {		
		
		JSONObject object = getCityApi(name);
		JSONArray tmp = new JSONArray();
		
		JSONArray tempArray = object.getJSONArray("list");		
		JSONObject sp;
		
		//parametri che ci interessano
		double temp;
		double temp_max;
		double temp_min;
		double temp_avg;
		double feels_like;
		String data;
		String main;
		String description;
		
		//i<9 perché sono prende le previsioni fino alle 24 ore successive
		//API prende dati ogni tre ore (8*3=24)
		for (int i=0; i<9; i++) {
			
			
			sp = tempArray.getJSONObject(i);
			temp =  (sp.getJSONObject("main").getDouble("temp"));
			temp_max = (sp.getJSONObject("main").getDouble("temp_max"));
			temp_min = (sp.getJSONObject("main").getDouble("temp_min"));
			feels_like = (sp.getJSONObject("main").getDouble("feels_like"));
			temp_avg = ((temp_max+temp_min)/2);
			
			JSONArray arr = sp.getJSONArray("weather");
			main = (String) (arr.getJSONObject(0).get("main"));
			description = (String) (arr.getJSONObject(0).get("description"));
			data = (String) sp.get("dt_txt");
			
			
			JSONObject giveback = new JSONObject();
			
			giveback.put("Temp", temp);
			giveback.put("Temp_max", temp_max);
			giveback.put("Temp_min", temp_min);
			giveback.put("Feels_like", feels_like);
			giveback.put("Temp_avg", temp_avg);
			giveback.put("Data", data);
			giveback.put("main", main);
			giveback.put("description", description);
			tmp.put(giveback);
	
		}
		return tmp;
	}
	
	
	
	/**
	 * Serve per suggerire all'utente una lista di città con id e stato di appartenenza
	 * 
	 * @return restituisce il JSONArray contenente le città da suggerire
	 */
	public JSONArray listOfCities() {	
		String name;
		String country;
		long id; 
		
		JSONArray tmp = new JSONArray();
		
		String[] names = {"Bologna", "Milano", "Ancona", "Torino"};
		
		for(int i =0; i<names.length; i++) {
			
			String s = names[i];
			JSONObject obj = getCityApi(s);
			JSONObject cityObj = obj.getJSONObject("city");
			name = (String)  cityObj.get("name");
			id = (int) cityObj.get("id");
			country = (String) cityObj.get("country");
			

			JSONObject giveback = new JSONObject();
			
			giveback.put("name", name);
			giveback.put("id", id);
			giveback.put("country", country);
			
			tmp.put(giveback);
		}
		return tmp;
	}
	
	
	/**
	 * Questo metodo prende le previsioni meteo future (temperatura reale, massima, minima, media e percepita).
	 * 
	 * @param name è il nome della città di cui si vogliono conoscere le previsioni future.
	 * @return un vettore di tipo City che contiene tutte le informazioni richieste e anche le informazioni sulla città.
	 * @throws IOException per errori di input e output 
	 * @throws MalformedURLException per errore di url
	 */
	
	public City getTempFutureApi(String name) {
		
		JSONObject obj = getCityApi(name);
		City gg = new City();
		gg = getCityInfofromApi(name);
		
		JSONArray wa = obj.getJSONArray("list");
		JSONObject cc = new JSONObject();
		
		Vector<Temperature> vec = new Vector<Temperature> (wa.length());
		
		try {
			for(int j = 0; j < wa.length(); j++){
				
				Temperature temps = new Temperature();
				cc = wa.getJSONObject(j);

				temps.setTemp(cc.getJSONObject("main").getDouble("temp"));
				temps.setTemp_max(cc.getJSONObject("main").getDouble("temp_max"));
				temps.setTemp_min(cc.getJSONObject("main").getDouble("temp_min"));
				temps.setFeels_like(cc.getJSONObject("main").getDouble("feels_like"));
				temps.setTemp_avg((temps.getTemp_max()+temps.getTemp_min())/2);
				temps.setData(cc.getString("dt_txt"));
				
				vec.add(temps); 
				}
			}
			catch(Exception e){
			
				e.printStackTrace();
			}
		gg.setTemps(vec);
		
		return gg;
	}

	/**
	 * Questo metodo serve per ottenere le informazioni sulla città dal sito di OpenWeather. 
	 * Viene richiamato da getTempFutureApi (String name).
	 * 
	 * @param nome della città.
	 * @return un oggetto di tipo City con le informazioni sulla città.
	 */
	public City getCityInfofromApi(String name) {
		
		JSONObject object = getCityApi(name);
		
		City city = new City(name);
		
		try {
			
			JSONObject cityObj = object.getJSONObject("city");
			String nameCity = (String) cityObj.get("name");
			String country = (String) cityObj.get("country");
			int id = (int) cityObj.get("id");
			city.setCountry(country);
			city.setId(id);
			city.setName(nameCity);
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return city;
	}
	
	
	
	/**
	 * Metedo per la ricerca della regex
	 * 
	 * @param regex indica la regex da cercare 
	 * @return JSONArray con all0interno la lista delle città con all'interno la regex richiesta
	 * @throws FileNotFoundException se il file non viene trovato
	 * @throws IOException per errore di input e output
	 * @throws ParseException per errore di parsing
	 * 
	 */
	
	public JSONArray Substring(String regex) throws FileNotFoundException, IOException, ParseException{
		
		
		List<City> city = new ArrayList<>();
		String path = System.getProperty("user.dir")+"/city.list.json";
		ObjectMapper mapper = new  ObjectMapper();
		city = mapper.readValue(new File(path), new TypeReference<ArrayList<City>>() {}); //converte tutto il json in un arrayList di oggetti
		
		List<String> name = new ArrayList<String>();
		for (City o : city){

			String n = o.getName();
			name.add(n);
			
			}

		Pattern p = Pattern.compile(regex);
		ArrayList<String> x = new ArrayList<String>();
		JSONArray match = new JSONArray();
		for (String s: name) {
		   if (p.matcher(s).matches()) {
			   
		      x.add(s);
		   }
		 }
		 JSONArray arr = new JSONArray(x);
		 return arr;
		
		
	}
	
	
	/**
	 * Questo metodo serve per andare a salvare in uno storico le temperature  
	 * dei 5 giorni successivi .
	 * 
	 * @param nome della città.
	 * @return il path file dove vengono salvate le informazioni.
	 * @throws ParseException  per errore di parsing
	 */
	
	public String save(String name) throws IOException, ParseException  {
		
		City city = getTempFutureApi(name);
		JSONClass tojson = new JSONClass();
		JSONObject obj = new JSONObject();
		obj = tojson.JSONClass(city);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();	//dtf.format(now)
		
		String s = name+ "_"+dtf.format(now);
		String path;
		path = System.getProperty("user.dir")+"\\"+s+".txt";
		
		try {
			
			PrintWriter write = new PrintWriter(new BufferedWriter( new FileWriter(path)));
			write.println(obj.toString());
			write.close();
		}
		catch(IOException e){
			
			System.out.println("ERRORE di I/O: "); 
			System.out.println(e);
		}
		
		return path;
	}
	
	
	/**
	 * 
	 * Questo metodo salva le previsioni ogni 5 ore.
	 * 
	 * @param name è il nome della città
	 * @return una stringa contenente il path del file salvato.
	 * 
	 */
	public String FiveHoursInfo(String name) {
		
		
		String report;
		report = System.getProperty("user.dir") + "/" + name + ".txt";
		File file = new File(report);
    
		
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		    	
		    	JSONArray tempApi = new JSONArray();
				tempApi = getTempApi(name);
		    	
		    	JSONObject oggetto = new JSONObject(); 
		    	oggetto = tempApi.getJSONObject(0);
		    	
		    	
		    			    	
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
		}, 0, 5, TimeUnit.HOURS); 
		
		return "I dati sono stati inseriti in " + report;
		 
	}	
	
	/**
	 * Questo metodo legge lo storico della città inserita dall'utente.
	 * 
	 * @param name è il nome della città di cui si vuole leggere lo storico.
	 * @return il JSONObject che contiene tutte le informazioni sulle temperature.
	 * @throws IOException per gli errori di input da file.
	 * @throws InvalidStringException per errore di stringa
	 */
	
	public JSONObject readHistory(String name) throws IOException, InvalidStringException {
		
        String string = "";
		
		string = System.getProperty("user.dir") + "/temperature/" + name + ".txt";
		
			
		String all = "";
		/*
		//Apriamo e leggiamo il file
		List<Temperature> city = new ArrayList<>();
		ObjectMapper mapper = new  ObjectMapper();
		city = mapper.readValue(new File(string), new TypeReference<ArrayList<Temperature>>() {}); 
		
		JSONArray temps = new JSONArray();
		for (Temperature o : city){

			double n = o.getTemp();
			temps.
			
			}
		*/
		
		BufferedReader br = new BufferedReader(new FileReader(string));
		
			try {
				
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			    	
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    
			    all = sb.toString();
			} finally {
				
			    br.close();
			}
				
			JSONObject obj = new JSONObject(all);
	
			return obj;
			
	}
	


	/**
	 * Questo metodo, utilizzando le informazioni dello storico e chiamando il metodo Calculate della classe Error Calculator,
	 * restituisce l'errore della città inserita dall'utente
	 * 
	 * @param names contiene i nomi di tutte le città su cui si vogliono fare statistiche sulla soglia di
	 *        errore e applicare i filtri.
	 * @param error è l'intero che rappresenta la soglia con cui si vuole filtrare.
	 * @param value esprime il filtro che si vuole applicare, cioè se si vuole sapere quali città hanno un errore maggiore,
	 *  minore o uguale dell'intero error che è stato inserito. Le stringhe ammesse sono: "$lt", "$gt" e "=".
	 * @param period rappresenta i giorni di predizione ( 1 o 5 ).
	 * @return restituisce un ArrayList di JSONObject filtrati 
	 * @throws EmptyStringException se uno dei nomi inseriti è una stringa vuota.
	 * @throws CitynotFoundException se l'utente ha inserito una città di cui non è presente lo storico. Le stringhe ammesse
	 *         sono: "Ancona","Milano","Torino","Bologna".
	 * @throws NotAllowedPeriodException se l'utente ha inserito un numero che non è compreso tra 1 e 5 (inclusi).
	 * @throws NotAllowedValueException se l'utente ha inserito una stringa non ammessa per il value.
	 * @throws IOException se si verificano problemi nella lettura del file.
	 * @throws InvalidStringException per eccezioni di stringa
	 */
	
	public ArrayList<JSONObject> HistoryOfError(ArrayList<String> names ,int error, String value,int period) throws EmptyStringException, CitynotFoundException, NotAllowedPeriodException, NotAllowedValueException, IOException, InvalidStringException {
		
		for(int i=0; i < names.size(); i++) {
			
			if(names.get(i).isEmpty()) {
				
				throw new EmptyStringException("Hai dimenticato di inserire la città");

			}
			else if(!(names.get(i).equals("Ancona") || names.get(i).equals("Milano") || names.get(i).equals("Torino") || names.get(i).equals("Bologna") )) {
				
				throw new CitynotFoundException("La città inserita non è presente nello storico");

			}
		}
			
		if(period < 1 || period > 5) {
			
	       throw new NotAllowedPeriodException(period + " è un periodo non valido. Inserisci un numero compreso tra 1 e 5 inclusi.");

		}
		
		if(!(value.equals("$gt") || value.equals("$lt") || value.equals("="))) {
			
			throw new NotAllowedValueException(value + " La stringa immessa non è consentita. Devi inserire una stringa tra \"$gt\", \"$lt\" e \"=\"");

		}
		
		Iterator<String> list = names.iterator();
		
		ArrayList<JSONArray> statisticArray = new ArrayList<JSONArray>(); 
		ArrayList<JSONObject> errors = new ArrayList<JSONObject>();
		
		while(list.hasNext()) {
			
			JSONArray array = new JSONArray();
			array = readHistory(list.next()).getJSONArray("OpenWeather");
			
			JSONArray tempInfo = new JSONArray();
			
			 /*
				JSONArray tempArray = new JSONArray ();
				JSONObject obj = new JSONObject(array);
				tempArray = obj.getJSONArray("OpenWeather");
				*/
				
				JSONArray tempDay = new JSONArray();
				/*
				JSONObject weather = new JSONObject();
				weather = array.getJSONObject(0);
				
				JSONArray temp = new JSONArray();
				temp = weather.getJSONArray("OpenWeather");
				*/
				
				for(int k = 0; k < array.length(); k++) {
					
					JSONObject visibility = new JSONObject();
					JSONObject all = new JSONObject();
					all = array.getJSONObject(k);
					
					visibility.put("temp", all.get("temp"));
					visibility.put("feels_like", all.get("feels_like"));
					visibility.put("temp_max", all.get("temp_max"));
					visibility.put("temp_min", all.get("temp_min"));
					visibility.put("data", all.get("data"));
					tempDay.put(visibility);
					
				}
				
				tempInfo.put(tempDay);
				
			statisticArray.add(tempInfo);
		}
		
		ErrorCalculator errorcalculator = new ErrorCalculator();
		errors = errorcalculator.calculate(names ,statisticArray, error, value, period);
		
		return errors;
	}
	
	
	
	/**
	 * Questo metodo dopo aver letto i file su cui sono salvate le 
	 * temperature per 1 giorno, 1 settimana o 1 mese,
	 * salva in un ArrayList di JSONArray le informazioni richieste di ogni città, 
	 * e lo passa al metodo per calcolare le statistiche.
	 * 
	 * @param cities rappresenta le città inserite
	 * @param period rappresenta il periodo di cui si vuole  la statistica.
	 * @throws EmptyStringException se la stringa è vuota.
	 * @throws CitynotFoundException se la città inserita non è tra quelle indicate
	 * @throws NotAllowedPeriodException se viene inserita una stringa errata per period.
	 * @throws IOException per errori di input e output
	 * @throws InvalidStringException per eccezioni delle stringhe
	 */
	
	public ArrayList<JSONArray> PeriodHistory(ArrayList<String> cities, String period) 
			throws EmptyStringException, CitynotFoundException, NotAllowedPeriodException, IOException, InvalidStringException {
		
		Iterator<String> pam1 = cities.iterator();
		Iterator<String> pam2 = cities.iterator();
		ArrayList<JSONArray> tempInfo = new ArrayList<JSONArray>();
		ArrayList<JSONArray> info = new ArrayList<JSONArray>();
		
		for(int i=0; i < cities.size(); i++) {
			
			if(cities.get(i).isEmpty()) {
				
				throw new EmptyStringException("Hai dimenticato di inserire la città");
			}
				
			else if(!(cities.get(i).equals("Ancona") || cities.get(i).equals("Milano") || cities.get(i).equals("Torino") || cities.get(i).equals("Bologna") )) {
				
				throw new CitynotFoundException("La città " + cities.get(i) +" non è presente nello storico");
			}
				
		}
		
		while(pam1.hasNext()) {
			
			JSONArray arrayTemp = new JSONArray();
			JSONObject obj = new JSONObject();
			obj = readHistory(pam1.next());
			arrayTemp.put(obj);
			tempInfo.add(arrayTemp);
			
		}
		
		int i = 0;
		while(pam2.hasNext()) {
			
			PeriodStatistics periodStats = new PeriodStatistics();
			JSONArray arrayStats = new JSONArray();
			
			if(period.equals("giornaliera")) {
				
				arrayStats = periodStats.DailyStats(pam2.next(),tempInfo.get(i));

				
			}
			else if(period.equals("settimanale")) {
				
				arrayStats = periodStats.OneWeekStats(pam2.next(),tempInfo.get(i));

				
			}
			else if(period.equals("mensile")) {
				
				arrayStats = periodStats.OneMonthStats(pam2.next(),tempInfo.get(i));

			}
			else {
				
				throw new NotAllowedPeriodException(period + " non è permessa. Devi inserire una stringa tra \"giornaliera\","
						+ "\"settimanale\" e \"mensile\". ");
			}
					
				
			info.add(arrayStats);
			i++;
		}
		
		return info;
		
	}
	

}
