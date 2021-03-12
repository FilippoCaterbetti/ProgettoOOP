package com.project.OPENWEATHER.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.EmptyStringException;

public class ServiceApplicationTest {

	private ServiceApplication service;
    private ArrayList<String> cities;
    
	 /**
     * Inizializza i componenti necessari a testare i metodi.
     * @throws java.lang.Exception
     */
	@BeforeEach
	void setUp() throws Exception {
		service = new ServiceApplication();
		cities = new ArrayList<String>();
		}

	/**
     * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
     * throws java.lang.Exception
     */
	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
     * Questo Test verifica se il path in cui viene salvato il file è corretto.
	 * @throws ParseException 
     */
	@Test
    @DisplayName("Salvataggio corretto del file")
    void salvaFile() throws IOException, ParseException {
    	
        String name = "Ancona";
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String today = date.format(new Date());
		
		String path = System.getProperty("user.dir") +name+"_"+today+ ".txt";
		
        assertEquals(path,service.save(name));
        
        
    }
    


	/**
	 * Questo Test verifica se viene generata correttamente l'eccezione CityNotFound.
	 */
    @Test
    @DisplayName("Corretta generazione dell'eccezione CityNotFoundException.")
    void readHistory1() throws ParseException{
		
    	cities.add("Bologna");
        cities.add("Mantova");
    	
        CitynotFoundException e = assertThrows(CitynotFoundException.class, () -> {service.HistoryOfError(cities,1,"max",1);});
    
        assertEquals("Città non trovata nello storico", e.getError());
        
    }
    

	/**
	 * Questo Test verifica se viene generata correttamente l'eccezione EmptyString.
	 */
	@Test
    @DisplayName("Corretta generazione dell'eccezione EmptyStringException.")
    void readHistory2() {
	
    	cities.add("Ancona");
        cities.add("");
    	
        EmptyStringException e = assertThrows(EmptyStringException.class, () -> {service.HistoryOfError(cities,1,"max",1);});
        
        assertEquals("Hai dimenticato di inserire la città...", e.getError());
        
    }
	
	
}