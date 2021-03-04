package com.project.OPENWEATHER.StatsAndFilters;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.project.OPENWEATHER.model.City;

public class TestStatistics {

	private FiltersStatistics Stats;
	
	private City city1;
	private City city2;
	
	private ArrayList<String> cities;
	private FilterVisibility filter;

	/**
	 * Inizializza i componenti necessari a testare i metodi.
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		cities = new ArrayList<String>();
		//filter = new FilterVisibility();
	}

	/**
	 * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

}
