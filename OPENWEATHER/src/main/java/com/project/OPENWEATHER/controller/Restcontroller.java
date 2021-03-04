package com.project.OPENWEATHER.controller;

import java.io.IOException;





import java.lang.Exception;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedParamException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.service.Service;
import com.project.OPENWEATHER.StatsAndFilters.*;


@RestController
public class Restcontroller {
	
	@Autowired
	Temperature stats = new Temperature();
	RealTempAvg tempavg = new RealTempAvg();
	TempMaxAvg maxavg = new TempMaxAvg();
	TempMinAvg minavg = new TempMinAvg();
	
	Service service;
	
	/**
	 * 
	 * * Rotta di tipo GET che mostra le temperature 
	 * per i 5 giorni successivi della città.
	 * 
	 * @param name indichiamo la città da cui vogliamo la temperatura.
	 * @return restituiamo le previsioni della città indicata
	 * 
	 */
	
	@GetMapping(value="/temp")
	public ResponseEntity<Object> getTemp(@RequestParam String name) {
		return new ResponseEntity<> (service.getTempApi(name), HttpStatus.OK);
    }
	
	 /**
	 *  @param name indichiamo la città da cui vogliamo la temperatura
	 * 	@return il file dove vengono salvati i dati.
	 * 	@throws IOException per gli errori di output del file.
	 */
		
	/*@GetMapping("/FiveHoursInfo")
	public ResponseEntity<Object> saveHour(@RequestParam String name) throws IOException {
		
		String file = service.FiveHoursInfo(name);
		
		return new ResponseEntity<> (file, HttpStatus.OK);
	}*/
	
	
	/*
	 * JSONObject deve essere come indicato:
	 * {
     *     "city": [
     *        {
     *          "name": "Ancona"
     *        },
     *      ],
     *     "period": "giornaliero"
     *  }*/
	
	//il "period"(giornaliero, settimanale, mensile) indica il periodo di tempo da selezionare
	@PostMapping(value="/PeriodCity")
	public ResponseEntity<Object> PeriodCity(@RequestBody String body) throws InvalidStringException, NotAllowedPeriodException{
		
		
		JSONObject object = new JSONObject(body);
		
		String name = object.getString("name");
		String period = object.getString("period");
		
		try {
			return new ResponseEntity<>(service.PeriodCity(name, period),HttpStatus.OK);
		}
		catch(InvalidStringException e) {
			return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
		}
		catch(NotAllowedPeriodException e) {
			return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Mostra le previsioni della città inserita nei 5 giorni successivi (temperatura massima, minima, reale e percepita)
	 * 
	 * @param name = città
	 */
	@GetMapping(value="/FiveDaysInfo")
	public ResponseEntity<Object> FiveDaysInfo(@RequestParam String name){
		
		return null;
		
	}
	
	/**
	 * Rotta GET che salva ogni 5 ore le previsioni della città inserita.
	 * 
	 * @param name è la città di cui salviamo le informazioni.
	 * @return il path dove viene salvato il file.
	 * @throws IOException per errori di output su file.
	 * 
	 */
	@GetMapping(value="/FiveHoursInfo")
    public ResponseEntity<Object> FiveHoursInfo(@RequestParam String name) throws IOException {
			String report5hour = service.FiveHoursInfo(name);
			
		return new ResponseEntity<> (report5hour, HttpStatus.OK);
	}
	
	
	
	/**
	 * Rotta POST che filtra le statistiche sulle temperature in base ad una soglia di errore e ai  giorni di predizione (da 1 a 5 giorni successivi)
	 * L'utente inserisce un JSONObject
	 * >, $gte >=, $lt <, $lte <=
	 * {
     *     "cities": [
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
	 * @throws NotAllowedValueException  stringa non ammessa
	 * @throws NotAllowedPeriodException  per invalid period.
	 * @throws IOException per errori di input da file.
	 */
	
	@PostMapping("/errors")
	public ResponseEntity<Object> filtersHistory(@RequestBody String body) 
			throws InvalidStringException, CitynotFoundException, NotAllowedValueException, NotAllowedPeriodException, IOException {
		
		JSONObject object = new JSONObject(body);
        JSONArray array = new JSONArray();

        array = object.getJSONArray("cities");
        
        ArrayList<String> cities = new ArrayList<String>(array.length());
        
        for(int i=0; i<array.length();i++) {
            JSONObject obj = new JSONObject();
            obj = array.getJSONObject(i);
            cities.add(obj.getString("name"));
        }
        
        int error = object.getInt("error");
        String value = object.getString("value");
        int period = object.getInt("period");
        
        try {
        	return new ResponseEntity<>(service.HistoryOfTemps(cities,error,value,period).toString(),HttpStatus.OK);
        }
        catch(InvalidStringException e) {
        	
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        }
        catch(CitynotFoundException e) {
        	
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        }
        catch(NotAllowedValueException e) {
        	
        	
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        }
        catch(NotAllowedPeriodException e) {
        	
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        }
		
	}
	
	
	/**
	 * Rotta di tipo GET che mostra le previsioni ristrette (temperatura massima, minima, percepita e
	 * visibilità) per i 5 giorni successivi alla richiesta della città inserita dall'utente.
	 * 
	 * @param cityName rappresenta la città di cui si richiedono le previsioni meteo ristrette.
	 * @return un JSONObject contenente le previsioni meteo ristrette della città richiesta e 
	 *         le informazioni generali su di essa.
	 */

	
	
	/**
	 * Rotta POST che mostra la media della temperatura massima, minima, percepita e la media, la minima,
	 * la massima 5 giorni, a seconda del periodo (giornaliero, settimanale, mensile) 
	 * 
	 * 
	 * {
     *		"city" : "name",
     *		"period" : "period"
	 *	}
	 * 
	 * @param body è il JSONObject 
	 * @return il JSONObject con le statistiche richieste.
	 * @throws NotAllowedPeriodException per period non ammessi
	 * @throws IOException per errori di lettura del file.
	 */
	
	@PostMapping(value="/stats")   //finire
    public ResponseEntity<Object> stats(@RequestBody String body) throws NotAllowedPeriodException, IOException {
		
		JSONObject req = new JSONObject(body);
		String period = req.getString("period");
		String cityName = req.getString("city");
		
		try {
			if(period.equals("giornaliero")) {
				req = object.toJSONOBject();
						
				return new ResponseEntity<> ( stats.   HttpStatus.OK);
			}
			
			else if(period.equals("settimanale")) {
				
				return new ResponseEntity<> (toString(), HttpStatus.OK);

			}
			else if(period.equals("mensile")) {
				
				return new ResponseEntity<> (statistic.fiveDayAverage(name).toString(), HttpStatus.OK);

			}
			else {
			
				throw new NotAllowedPeriodException("Questo "+ period + " è invalido ");
				
			}
		}catch (NotAllowedPeriodException e) {
			
			return new ResponseEntity<> (e.getError(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	/**
	 * Rotta di tipo POST che filtra in base al periodo le statistiche sulle temperature della città
	 * Il JSONObject deve essere di questo tipo per funzionare: 
	 * 
	 * {
     *     "cities": [
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
	 * 
	 */
	
	@PostMapping(value="/statsHistory")
    public ResponseEntity<Object> statsHistory(@RequestBody String body) 
    		throws InvalidStringException, CitynotFoundException, NotAllowedPeriodException, IOException {
		
		JSONObject object = new JSONObject(body);
        JSONArray array = new JSONArray();

        array = object.getJSONArray("cities");
        
        ArrayList<String> cities = new ArrayList<String>(array.length());
        
        for(int i=0; i<array.length();i++) {
        	
            JSONObject obj = new JSONObject();
            obj = array.getJSONObject(i);
            cities.add(obj.getString("name"));
        }
		
        String period = object.getString("period");
        
        try {
        	return new ResponseEntity<>(service.HistoryOfTemps(cities,period).toString(),HttpStatus.OK);
        }
        
        catch (CitynotFoundException e) {
        	
			return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
		
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
     *     "cities": [
     *        {
     *          "name": ""
     *        },
     *        {
     *          "name": ""
     *        }
     *      ],
     *     "param": "max",
     *     "period": "giornaliero"
     *  }
	 * 
	 * a seconda del "param"(temp max o min o feels_like o average) della città 
	 * e in che "period"(giornaliero, settimanale o mensile).
	 * 
	 * 
	 * @param body è un JSONObject 
	 * @return il JSONArray che contiene tanti JSONObject quante sono le città specificate nella richiesta
	 *         ognuno dei quali contiene il nome della città e la media del "param" indicato. In più il JSONArray contiene
	 *         un ultimo JSONObject al cui interno è contenuta la massima o minima media a seconda del valore indicato.
	 * @throws NotAllowedPeriodException se il numero immesso è errato.
	 * @throws NotAllowedParamException se viene inserita una stringa errata per param.
	 * @throws InvalidStringException 
	 *  
	 */
	@PostMapping(value="/filters")
	public ResponseEntity<Object> filters(@RequestBody String body) throws NotAllowedPeriodException, NotAllowedParamException, InvalidStringException {
		
		JSONObject obj = new JSONObject(body);
        JSONArray arr = new JSONArray();

 

        arr = obj.getJSONArray("cities");
        
        ArrayList<String> cities = new ArrayList<String>(arr.length());
        
        for(int i=0; i<arr.length();i++) {
        	
            JSONObject object = new JSONObject();
            object = arr.getJSONObject(i);
            cities.add(object.getString("name"));
        }
        
        String param = obj.getString("param");
        String period = obj.getString("period");
		
        Filters filter;
        
		filter = new Filters(cities,param, period);
		
		try {
        	return new ResponseEntity<>(filter.analyze().toString(),HttpStatus.OK);
        }
		catch(NotAllowedPeriodException e) {
			
	        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
	        	
	        }
		/*catch(NotAllowedParamException e) {
			
        	return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        	
        }
        */
		
		
	}
	
	
	
	
	
	
	
	
}
