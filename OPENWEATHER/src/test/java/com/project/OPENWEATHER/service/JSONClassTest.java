
package com.project.OPENWEATHER.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

public class JSONClassTest {
	
	
	City city;
	Vector<Temperature> temp;
	JSONClass tojson;
	
	/**
     * Inizializza i componenti necessari a testare i metodi.
     * @throws java.lang.Exception
     */
	@BeforeEach
	void setUp() throws Exception {
		city = new City();
		temp = new Vector<Temperature>();
		tojson = new JSONClass();
	}

	/**
     * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
     * throws java.lang.Exception
     */
	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
     * Questo Test verifica se l'oggetto city viene converito correttamente in JSONObject.
     */
	@Test
    @DisplayName("Conversione effettuata")
    void toJson() throws IOException {
    	
        city.setName("Ancona");
        city.setId(42);
        city.setCountry("IT");
        Temperature temps = new Temperature(25,30,40,20,30,"15-03-2021");
        temp.add(temps);
        city.setTemps(temp);
        
        JSONObject obj = new JSONObject();
        
        obj.put("name", city.getName());
        obj.put("country", city.getCountry());
		obj.put("id", city.getId());
        
		JSONArray tempArr = new JSONArray();
		
		JSONObject tempObj = new JSONObject();
	

		tempObj.put("temp", (temps.getTemp()));
		tempObj.put("feels_like", (temps.getFeels_like()));
		tempObj.put("temp_max", (temps.getTemp_max()));
		tempObj.put("temp_min", (temps.getTemp_min()));
		tempObj.put("temp_avg", (temps.getTemp_avg()));

		tempObj.put("data", (temps.getData()));
		
		tempArr.put(tempObj);
		
		obj.put("OpenWeather", tempArr);
		
		assertEquals(obj.toString(),tojson.JSONClass(city).toString());
		
    }

	
}
