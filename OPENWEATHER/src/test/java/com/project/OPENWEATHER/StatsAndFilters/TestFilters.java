package com.project.OPENWEATHER.StatsAndFilters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.exception.NotAllowedValueException;

class TestFilters {
	
	private Filters filter;
	private ArrayList<String> cities;
	
	/**
     * Inizializza i componenti necessari a testare i metodi.
     * @throws java.lang.Exception
     */
	@BeforeEach
	void setUp() throws Exception {
		cities = new ArrayList<String>();
	}

	/**
     * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
     */
	@AfterEach
	void tearDown() throws Exception {
	}

	
	/**
	 * Questo Test verifica se viene generata correttamente l'eccezione NotAllowedException.
	 */
    @Test
   @DisplayName("Corretta generazione dell'eccezione NotAllowedPeriod.")
    void analyze1() {
		
    	cities.add("Ancona");
    	cities.add("Milano");
        cities.add("Macerata");
        
        filter = new Filters(cities,"temp","max",10);
    	
        NotAllowedPeriodException e = assertThrows(NotAllowedPeriodException.class, () -> {filter.analyze();});
    
        assertEquals("10 non è un periodo ammesso. Inserisci un numero che sia o 1 o 7 o 30.", e.getError());
        
    }
    
    /**
	 * Questo Test verifica se viene generata correttamente l'eccezione WrongParam.
	 */
    @Test
    //
    @DisplayName("Corretta generazione dell'eccezione NotAllowedParam.")
    void analyze2() {
		
    	cities.add("Ancona");
    	cities.add("Milano");
        cities.add("Macerata");
        
        filter = new Filters(cities,"temp", "MaX",1);
    	
        NotAllowedPeriodException e = assertThrows(NotAllowedPeriodException.class, () -> {filter.analyze();});
    
        assertEquals("MaX non è una stringa ammessa.Inserisci una stringa tra temp_min,temp_max,feels_like e temp", e.getError());
        
    }
}
