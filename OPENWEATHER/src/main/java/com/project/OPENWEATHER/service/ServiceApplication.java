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
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.project.OPENWEATHER.StatsAndFilters.PeriodStatistics;
import com.project.OPENWEATHER.error.ErrorCalculator;
import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.EmptyStringException;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.JSONClass;
import com.project.OPENWEATHER.model.Temperature;


public class ServiceApplication implements com.project.OPENWEATHER.service.Service {
	
	private String Api_key = "666efac3e1caf3f728f8c5860edeb469";

	//RestTemplate permette di effettuare la richiesta e di convertire 
	//automaticamente il json ricevuto in un oggetto Java strutturato in maniera 
	//conforme al json atteso; Noi di seguito usiamo questa funzionalità e trasferiamo i dati
	//in un tipo JSONObject che useremo poi in seguito nelle altre classi
	public JSONObject getCityApi(String name) throws MalformedURLException, IOException, ParseException {
		
		
		JSONObject obj;
		
		
		//Questo serve epr avere le temperature in gradi centigradi
		String unit = "&units=metric";
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + name + unit + "&appid="+Api_key;
		
		RestTemplate rt = new RestTemplate();
		
		obj = new JSONObject(rt.getForObject(url, String.class));
		
		return obj;

	}

	
	/**
	 * 
	 * Questo metodo utilizza getCityWeather per andare a prendere i dati sulle varie temperature della città.
	 * @param name è il nome della città
	 * @return restituisce il JSONArray contente la temperatura reale 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * 
	 */

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
		//String data;
		String main;
		String description;
		
		for (int i = 0; i<TempArray.length(); i++) {
			
			sp = TempArray.getJSONObject(i);
			temp = Double.parseDouble(sp.get("temp").toString());
			temp_max = Double.parseDouble(sp.get("temp_max").toString());
			temp_min = Double.parseDouble(sp.get("temp_min").toString());
			temp_avg = Double.parseDouble(sp.get("temp_avg").toString());
			feels_like = Double.parseDouble(sp.get("feels_like").toString());
			//data =  sp.get("dt_txt").toString(); //?
			main = sp.get("main").toString();
			description= sp.get("description").toString();
			
			JSONObject g = new JSONObject();
			g.put("temp", temp);
			g.put("temp_max", temp_max);
			g.put("temp_min", temp_min);
			g.put("temp_avg", temp_avg);
			g.put("feels_like", feels_like);
			//g.put("Data", data);
			g.put("main", main);
			g.put("description", description);
			tmp.put(g);
			
		}
		return tmp;
	}
	
	
	
	/**
	 * Metedo per la ricerca delle regex
	 * 
	 * @param regex indica la regex da cercare 
	 * @return JSONArray con all0interno la lista delle città con all'interno la regex richiesta
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	public JSONArray Substring(String regex) throws FileNotFoundException, IOException, ParseException{
		

			JSONParser parser = new JSONParser();
			List<String> names = new ArrayList<>();

			JSONArray a = (JSONArray) parser.parse(new FileReader("city.list.json"));
			for (Object o : a){
					
				JSONObject person = (JSONObject) o;
				String name = (String) person.get("name");
				names.add(name);
			}

			ArrayList<String> matches = new ArrayList<String>();

			Pattern p = Pattern.compile(regex);

			 for (String s:names) {
			   if (p.matcher(s).matches()) {
			      matches.add(s);
			   }
			 }
			 JSONArray jsArray = new JSONArray(matches);
			 
			 return jsArray;
	}
	
	
	
	
	
	/**
	 *  Questo metodo ci serve per andare a salvare le temperature  
	 * dei 5 giorni successivi e le salva in uno storico.
	 * @param nome della città.
	 * @return file dove vengono salvate le informazioni.
	 * @throws ParseException 
	 */
	
	public String save(String name) throws IOException, ParseException  {
		
		City city = getTempFutureApi(name);
		JSONObject obj = new JSONObject();
		obj = city.toJSONObject();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
		LocalDateTime now = LocalDateTime.now();	//dtf.format(now)
		
		String s = name+dtf.format(now)+".txt";
		String path;
		path = System.getProperty("user.dir")+"/"+s+"report.txt";

		
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
		
		return "I dati sono stati inseriti";
		 
	}	
	
	/**
	 * Questo metodo della lettura dello storico della città passata in ingresso. A seconda che il flag sia true o false, il 
	 * metodo andrà a leggere lo storico per il calcolo della soglia di errore e delle previsioni azzeccate oppure per 
	 * le statistiche sulla visibilità.
	 * 
	 * @param name è il nome della città di cui si vuole leggere lo storico.
	 * @param choiche è la stringa che l'utente digita per scegliere se vuole le statistiche degli errori o solo le statistiche
	 * @return il JSONArray che contiene tutte le informazioni sulla visibilità.
	 * @throws IOException se si verificano errori di input da file.
	 * @throws InvalidStringException 
	 */
	
	public JSONArray readHistory(String name, String choiche) throws IOException, InvalidStringException {
		
        String string = "";
		if(choiche == "errors") {
			
			string = System.getProperty("user.dir") + "/errors/" + name +".txt";
		}
		else if (choiche == "temperature") {
			string = System.getProperty("user.dir") + "/temperature/" + name +".txt";
		}
		else throw new InvalidStringException(string); 
			
		String lines;
		
		//Apriamo e leggiamo il file	
		
		BufferedReader br = new BufferedReader(new FileReader(string));
		
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    lines = sb.toString();
			} finally {
			    br.close();
			}
				
		
			JSONArray array = new JSONArray(lines);
	
			return array;
			
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
	 * @throws InvalidStringException 
	 */
	
	public ArrayList<JSONObject> HistoryOfError(ArrayList<String> names ,int error, String value,int period) throws EmptyStringException, CitynotFoundException, NotAllowedPeriodException, NotAllowedValueException, IOException, InvalidStringException {
		
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
			array = readHistory(list.next(),"errors");
			JSONArray tempInfo = new JSONArray();
			
			for(int i=0; i < array.length(); i++) {
				
				JSONArray tempDay = new JSONArray();
				
				JSONObject weather = new JSONObject();
				weather = array.getJSONObject(i);
				
				JSONArray temp = new JSONArray();
				temp = weather.getJSONArray("Weather");
				
				
				for(int k = 0; k < temp.length(); k++) {
					
					JSONObject visibility = new JSONObject();
					JSONObject all = new JSONObject();
					all = temp.getJSONObject(k);
					
					visibility.put("temperature", all.get("temperature"));
					visibility.put("data", all.get("data"));
					tempDay.put(visibility);
					
				}
				
				tempInfo.put(tempDay);
				
			}
			
			statisticArray.add(tempInfo);
		}
		
		ErrorCalculator errorcalculator = new ErrorCalculator();
		errors = errorcalculator.calculate(names ,statisticArray, error, value, period);
		
		return errors;
	}
	
	
	
	/**
	 * Questo metodo va a richiamare readHistory per leggere i file su cui sono salvate le informazioni relative 
	 * alle temperature per 1 giorno/1 settimana/1mese. Dopo aver salvato in un ArrayList di JSONArray le informazioni di ogni città, 
	 * lo passa al metodo che serve per calcolare le statistiche sulle temperature.
	 * 
	 * @param cities rappresenta i nomi delle città su cui si vogliono fare statistiche.
	 * @param period rappresenta il periodo su cui si vuole fare la statistica.
	 * @throws EmptyStringException se almeno una delle stringhe immesse è vuota.
	 * @throws CityNotFoundException se la città immessa non è una tra quelle indicate sopra.
	 * @throws WrongPeriodException se viene inserita una stringa errata per period.
	 * @throws IOException se si verifica un errore di lettura del file.
	 * @throws InvalidStringException 
	 */
	
	public ArrayList<JSONArray> PeriodHistory(ArrayList<String> cities, String period) 
			throws EmptyStringException, CitynotFoundException, NotAllowedPeriodException, IOException, InvalidStringException {
		
		Iterator<String> pam1 = cities.iterator();
		Iterator<String> pam2 = cities.iterator();
		ArrayList<JSONArray> tempInfo = new ArrayList<JSONArray>();
		ArrayList<JSONArray> info = new ArrayList<JSONArray>();
		
		for(int i=0; i < cities.size(); i++) {
			if(cities.get(i).isEmpty())
				throw new EmptyStringException("Hai dimenticato di inserire la città");
			else if(!(cities.get(i).equals("Ancona") || cities.get(i).equals("Milano") || cities.get(i).equals("Roma") || cities.get(i).equals("Bologna") || cities.get(i).equals("Parigi")))
				throw new CitynotFoundException("La città inserita non è presente nello storico");
		}
		
		while(pam1.hasNext()) {
			
			JSONArray arrayTemp = new JSONArray();
			arrayTemp = readHistory(pam1.next(),"temperature");
			tempInfo.add(arrayTemp);
			
		}
		
		int i = 0;
		while(pam2.hasNext()) {
			
			PeriodStatistics stats = new PeriodStatistics();
			JSONArray arrayStats = new JSONArray();
			
			if(period.equals("giornaliera"))
				arrayStats = stats.DailyStats(pam2.next(),tempInfo.get(i));
			else if(period.equals("settimanale"))
				arrayStats = stats.OneWeekStats(pam2.next(),tempInfo.get(i));
			else if(period.equals("mensile"))
				arrayStats = stats.OneMonthStats(pam2.next(),tempInfo.get(i));
			else throw new NotAllowedPeriodException(period + "non è permessa. Devi inserire una stringa tra \"giornaliera\","
					+ "\"settimanale\" e \"mensile\". ");
				
			info.add(arrayStats);
			i++;
		}
		
		return info;
		
	}
	
	
	
	/**
	 * Questo metodo prende le previsioni meteo future (temperatura
	 * massima, minima, percepita).
	 * @param name è il nome della città di cui si vogliono conoscere le previsioni ristrette.
	 * @return un vettore di tipo City che contiene tutte le informazioni richieste e anche le informazioni sulla città.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	
	public City getTempFutureApi(String name) throws MalformedURLException, IOException, ParseException {
		City gg = new City();
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		arr = getTempApi(name);
		
		JSONArray wa = obj.getJSONArray("ciao");
		JSONObject cc = new JSONObject();
		
		Vector<Temperature> vec = new Vector<Temperature> (wa.length());
		
		try {
			for(int j = 0; j < wa.length(); j++){
				Temperature temp = new Temperature();
				cc = wa.getJSONObject(j);
				temp.setTemp(cc.getDouble("temp"));
				temp.setTemp_max(cc.getDouble("temp_max"));
				temp.setTemp_min(cc.getDouble("temp_min"));
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






}
