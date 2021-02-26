package com.project.OPENWEATHER.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.model.Statistics;
import com.project.OPENWEATHER.service.Service;

/*
 * import org.springframework.web.bind.annotation.GetMapping;
 *import org.springframework.web.bind.annotation.RequestParam;
 *import org.springframework.web.bind.annotation.RestController;
 * */
 
//DA FINIRE

@RestController

public class Restcontroller {
	
	@Autowired 
	Statistics stats = new Statistics();
	Service service;
	
	
	//con @param name indichiamo la città da cui vogliamo la temperatura.
	//con @return restituiamo le previsioni della città indicata
	
	@GetMapping("/temp")
	public ResponseEntity<Object> getTemp(@RequestParam String name) {
		return new ResponseEntity<> (service.getTempfromApi(name), HttpStatus.OK);
    }
	
	 /* @param name indichiamo la città da cui vogliamo la temperatura
	 * @return il file dove vengono salvati i dati.
	 * @throws IOException per gli errori di output del file.
	 */
		
	@GetMapping("/FiveHourInfo")
public ResponseEntity<Object> saveHour(@RequestParam String name) throws IOException {
		
		String file = service.FiveHourInfo(name);
		
		return new ResponseEntity<> (file, HttpStatus.OK);
	}
	
	
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
	@PostMapping("/PeriodCity")
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
	 * @param name = città.
	 */
	@GetMapping("/FiveDaysInfo")
	public ResponseEntity<Object> FiveDaysInfo(@RequestParam String name){
		return
	}
	
	
	
}
