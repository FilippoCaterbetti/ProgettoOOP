package com.project.OPENWEATHER.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.OPENWEATHER.model.City;

class TestCity {

	private City citta1;
	private City citta2;

	/**
	 * Inizializza i componenti necessari a testare i metodi.
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		citta1 = new City("Milan", "Italy", 1);
		citta2 = new City("Roma", "Italy", 2);
	}

	/**
	 * Serve per distruggere ciò che è stato inizializzato dal metodo setUp.
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/*
	 * serve per controllare che i parametri passati siano giusti
	 */
	@Test
	void testToJSONObject() {
		assertEquals("Milan", citta1.toJSONObject().get("name"));
		assertEquals("Italy", citta1.toJSONObject().get("country"));
		assertEquals(1L, citta1.toJSONObject().get("id"));
	}

}
