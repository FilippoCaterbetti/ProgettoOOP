package com.project.OPENWEATHER.controller;

import java.io.IOException;

import java.lang.Exception;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.EmptyStringException;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedParamException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.JSONClass;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.service.Service;
import com.project.OPENWEATHER.service.ServiceApplication;
import com.project.OPENWEATHER.StatsAndFilters.*;


@RestController
public class Restcontroller {
	
	@Autowired
	Temperature stats = new Temperature();
	ServiceApplication service = new ServiceApplication();
	Statistics statistic = new Statistics();
	
	/**
	 * 
	 *  Rotta POST che mostra le temperature per i 5 giorni successivi della città.
	 * 	
	 * {
     *
     *       "name": "Ancona"
     *        
     *  }      
	 * 
	 * 
	 * @param name indica la città da cui vogliamo la temperatura.
	 * @return restituiamo le previsioni della città indicata
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * 
	 */
	
	@PostMapping(value="/temps")
	public ResponseEntity<Object> getTemp(@RequestBody String body) throws MalformedURLException, IOException, ParseException {
		
		JSONObject req = new JSONObject(body);
		
		String name = req.getString("name");
		JSONArray reg = new JSONArray();
		reg = service.getTempApi(name);
		
		
		return new ResponseEntity<> (reg.toString(), HttpStatus.OK);
		
    }
	
	/**
	 * Rotta  GET che mostra le temperature future (temperatura massima, minima, percepita e
	 * media) dei 5 giorni successivi della città
	 * 
	 * @param name è la città 
	 * @return un JSONObject contenente temperature
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	
	@GetMapping(value="/OpenWeather")
	public ResponseEntity<Object> getCityApi(@RequestParam String name) throws MalformedURLException, IOException, ParseException{
		
		City citta = service.getTempFutureApi(name);
		JSONObject object = new JSONObject();
		
		JSONClass gjson = new JSONClass();
		
		object = gjson.JSONClass(citta);
		
		return new ResponseEntity<>(object.toString(),HttpStatus.OK);
		
	}
	 /**
	  *Rotta GET che salva ogni cnque ore le temperature della città.
	  * 
	 *  @param name indica la città da cui vogliamo la temperatura
	 * 	@return il file dove vengono salvati i dati.
	 * 	@throws IOException per gli errori di output del file.
	 */
		
	@GetMapping("/FiveHoursInfo")
	public ResponseEntity<Object> saveFiveHour(@RequestParam String name) throws IOException {
		
		String file = service.FiveHoursInfo(name);
		
		return new ResponseEntity<> (file, HttpStatus.OK);
	}
	
	
	/**
	 * Rotta POST che filtra le statistiche sulle temperature in base ad una soglia di errore e ai  giorni di predizione (da 1 a 5 giorni successivi)
	 * L'utente inserisce un JSONObject
	 * >, $gte >=, $lt <, $lte <=
	 * {
     *     "città": [
     *        {
     *          "name": "Ancona"
     *        }
     *      ],
     *     "error": 1,
     *     "value": "$gt" 
     *     "period": 3    
     *  }
	 * 
	 * @param body è un JSONObject 
	 * @return un JSONArray contenente i JSONObject con tutte le informazioni sulle previsioni azzeccate 
	 * @throws CityNotFoundException per errori di città
	 * @throws NotAllowedStringException se città vuota
	 * 
	 * @throws NotAllowedPeriodException  per invalid period.
	 * @throws IOException per errori di input da file.
	 * @throws NotAllowedValueException 
	 * @throws EmptyStringException 
	 */
	
	@PostMapping(value="/errors")
	public ResponseEntity<Object> filtersHistory(@RequestBody String body) 
			throws InvalidStringException, CitynotFoundException, NotAllowedPeriodException, IOException, EmptyStringException, NotAllowedValueException { //NotAllowedValueException
		
		JSONObject object = new JSONObject(body);
        JSONArray array = new JSONArray();

        array = object.getJSONArray("città");
        
        ArrayList<String> names = new ArrayList<String>(array.length());
        
        for(int i=0; i<array.length();i++) {
        	
            JSONObject obj = new JSONObject();
            obj = array.getJSONObject(i);
            names.add(obj.getString("name"));
            
        }
        
        int error = object.getInt("error");
        String value = object.getString("value");
        int period = object.getInt("period");
        
        try {
        	
        	return new ResponseEntity<>(service.HistoryOfError(names,error,value,period).toString(),HttpStatus.OK);
        }
        catch(CitynotFoundException e) {
        	
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        }
        catch(NotAllowedPeriodException e) {
        	
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        }
		
	}


	
	
	/**
	 * Rotta POST che mostra la media della temperatura massima, minima, percepita e la media, la minima,
	 * la massima 5 giorni, a seconda del periodo (da oggi a 5 giorni )
	 * 
	 * 
	 * {
     *		"città" : "name",
     *		"period" : "oggi"
	 *	}
	 * 
	 * @param body è il JSONObject 
	 * @return il JSONObject con le statistiche richieste.
	 * @throws NotAllowedPeriodException per period non ammessi
	 * @throws IOException per errori di lettura del file.
	 * @throws ParseException 
	 */
	
	@PostMapping(value="/stats") 
    public ResponseEntity<Object> stats(@RequestBody String body) throws NotAllowedPeriodException, IOException, ParseException {
		
		JSONObject req = new JSONObject(body);
		
		String cityName = req.getString("città");
		String period = req.getString("period");
		
		try {
			if(period.equals("oggi")) {		
				
				return new ResponseEntity<>(statistic.todayAvg(cityName).toString(), HttpStatus.OK);
			}
			
			else if(period.equals("5 giorni")) {
				
				return new ResponseEntity<> (statistic.fiveDayAvg(cityName).toString(), HttpStatus.OK);

			}
			else {
			
				throw new NotAllowedPeriodException("Questo"+ period + "è invalido");
				
			}
		}catch (NotAllowedPeriodException e) {
			
			return new ResponseEntity<> (e.getError(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Rotta POST per cercare una regex all'interno della lista delle città disponibili in 
	 * OPENWEATHER e restituire un JSONArrray con i nomi delle città
	 * 
	 * Body deve essere di questo tipo
	 * con .*to.* per esempio cerchiamo tutte le città con all'interno la stringa to ovunque sia posizionata all'interno del nome
	 * 
	 * {
     *		"regex" : ".*to.*"
	 *	}
	 * 
	 * @param body è il JSONObject 
	 * @return il JSONObject con le statistiche richieste.
	 * @throws FileNotFoundException
	 * @throws IOException per errori di lettura del file.
	 * @throws ParseException 
	 */
	@PostMapping(value="/findRegex") 
    public ResponseEntity<Object> Substring(@RequestBody String body) throws FileNotFoundException, IOException, ParseException {
		
		JSONObject req = new JSONObject(body);
		
		String regex = req.getString("regex");
		JSONArray reg = new JSONArray();
		reg = service.Substring(regex);
			
		return new ResponseEntity<>(reg.toString(), HttpStatus.OK);
				

	}
	
	
	
	
	
	/**
	 * Rotta di tipo POST che filtra in base al periodo le statistiche sulle temperature della città
	 * Il JSONObject deve essere di questo tipo per funzionare: 
	 * 
	 * {
     *     "città": [
     *        {
     *          "name": "Ancona"
     *        },
     *        {
     *          "name": "Bologna"  //nel caso fi piu città
     *        }
     *      ],
     *     "period": "giornaliero"
     *  }
	 * 
	 * il "period" può essere solo giornaliero, settimanale, mensile.
	 * . Le città ammesse sono solo Ancona, Campobasso, Macerata, Roma, San Martino in Pensilis e Tolentino.
	 * 
	 * @param body è il JSONObject sopra.
	 * @return  statistiche delle città con la periodicità indicata.
	 * 
	 * @throws InvalidStringException se la stringa è errata/vuota.
	 * @throws CitynotFoundException se la città non esiste.
	 * @throws NotAllowedPeriodException se viene inserita un  period errato
	 * @throws IOException se ci sono errori di input da file.
	 * @throws EmptyStringException 
	 * 
	 */
	
	@PostMapping(value="/statsHistory")
    public ResponseEntity<Object> statsHistory(@RequestBody String body) throws InvalidStringException, CitynotFoundException, NotAllowedPeriodException, IOException, EmptyStringException {
		
		JSONObject object = new JSONObject(body);
        JSONArray array = new JSONArray();

        array = object.getJSONArray("città");
        
        ArrayList<String> cities = new ArrayList<String>(array.length());
        
        String period = object.getString("period");

        
        for(int i=0; i<array.length(); i++) {
        	
            JSONObject obj = new JSONObject();
            obj = array.getJSONObject(i);
            cities.add(obj.getString("name"));
            
        }
		
        
        try {
        	return new ResponseEntity<>(service.PeriodHistory(cities,period).toString(),HttpStatus.OK);
        }
        catch (InvalidStringException e) {
        	
			return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
		
        }
        
        catch (NotAllowedPeriodException e) {
        	
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        
        }
                
	}
	
	
	/**
	 * Rotta  POST che filtra le statistiche in base alle informazioni che si vogliono
	 * è richiesto un JSONObject di questo tipo:
	 * 
	 * {
     *     "città": [
     *        {
     *          "name": ""
     *        },
     *        {
     *          "name": ""
     *        }
     *      ],
     *     "period": 1
     *     "param" : "temp_max"
     *     "value": "max"
     *  }
	 * 
	 *  "param"(temp max o min o feels_like o average) della città 
	 * e in che "period"(da 1 a 5 giorni).
	 * 
	 * 
	 * @param body è un JSONObject 
	 * @return il JSONArray che contiene tanti JSONObject quante sono le città specificate nella richiesta
	 *         ognuno dei quali contiene il nome della città
	 *         In più il JSONArray contienenun ultimo JSONObject al cui interno è contenuta la massima o minima media a seconda del valore indicato.
	 * @throws NotAllowedPeriodException se il numero immesso è errato.
	 * @throws InvalidStringException 
	 * @throws NotAllowedValueException 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 *  
	 */
	@PostMapping(value="/filters")
	public ResponseEntity<Object> filters(@RequestBody String body) throws NotAllowedPeriodException, NotAllowedParamException, InvalidStringException, NotAllowedValueException, MalformedURLException, IOException, ParseException {
		
		JSONObject obj = new JSONObject(body);
        JSONArray arr = new JSONArray();
        arr = obj.getJSONArray("città");
        
        ArrayList<String> cities = new ArrayList<String>(arr.length());
        
        for( int i=0; i<arr.length(); i++) {
        	
            JSONObject object = new JSONObject();
            object = arr.getJSONObject(i);
            cities.add(object.getString("name"));
        }
        
        int period = obj.getInt("period");
		String param = obj.getString("param");
		String value = obj.getString("value");
		
        Filters filter;
       
		filter = new Filters(cities, param, value, period);
		
		try {
        	return new ResponseEntity<>(filter.analyze().toString(),HttpStatus.OK);
        }
		catch(NotAllowedPeriodException e) {
			
	        	return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
	        	
	        }catch(NotAllowedValueException e) {
	        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
	        }
			catch(NotAllowedParamException e) {
	        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
	        }	
		
	}
	
}
