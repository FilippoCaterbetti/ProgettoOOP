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
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.service.Service;


@RestController
public class Restcontroller {
	
	@Autowired
	Temperature stats = new Temperature();
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
	
	
	
	
	
	
	
	
	
}
