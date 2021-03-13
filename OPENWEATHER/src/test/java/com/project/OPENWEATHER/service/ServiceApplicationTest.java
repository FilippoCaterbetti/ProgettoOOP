package com.project.OPENWEATHER.service;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.OPENWEATHER.exception.CitynotFoundException;
import com.project.OPENWEATHER.exception.EmptyStringException;


public class ServiceApplicationTest {

	private ServiceApplication service;
    private ArrayList<String> names;
    
	 /**
     * Inizializza i componenti necessari a testare i metodi.
     * @throws java.lang.Exception
     */
	@BeforeEach
	void setUp() throws Exception {
		service = new ServiceApplication();
		names = new ArrayList<String>();
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
    	
        String name = "Milano";
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String today = date.format(new Date());
		
		String path = System.getProperty("user.dir") +name+"_"+today+ ".txt";
		
        assertEquals(path,service.save(name));
        
        
    }
    


	
	// Questo Test verifica se viene generata correttamente l'eccezione CityNotFound.
	 
    @Test
    @DisplayName("Corretta generazione dell'eccezione CityNotFoundException.")
    void readHistory1() throws ParseException{
		
    	names.add("Bologna");
        names.add("Mantova");
    	
        CitynotFoundException e = assertThrows(CitynotFoundException.class, () -> {service.HistoryOfError(names,1,"max",1);});
    
        assertEquals("La città inserita non è presente nello storico", e.getError());
        
    }
    

	
	//Questo Test verifica se viene generata correttamente l'eccezione EmptyString.
	 
	@Test
    @DisplayName("Corretta generazione dell'eccezione EmptyStringException.")
    void readHistory2() {
	
    	names.add("Ancona");
        names.add("");
    	
        EmptyStringException e = assertThrows(EmptyStringException.class, () -> {service.HistoryOfError(names,1,"max",1);});
        
        assertEquals("Hai dimenticato di inserire la città", e.getError());
       
    }
	
	
}