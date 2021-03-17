package com.project.OPENWEATHER.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;

class TestTemperature {

	private Temperature number;

	/**
	 * Inizializza i componenti necessari a testare i metodi.
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		number = new Temperature(17, 15, 30, 0, "15-03-2021");
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
	 * serve per controllare se i parametri inseriti sono giusti
	 */
	@Test
	void toJSONObject() {
		assertEquals(17D, number.toJSONObject().get("temp"));
		assertEquals(15D, number.toJSONObject().get("feels_like"));
		assertEquals(30D, number.toJSONObject().get("temp_max"));
		assertEquals(0D, number.toJSONObject().get("temp_min"));

		assertEquals("15-03-2021", number.toJSONObject().get("data"));
	}

}
