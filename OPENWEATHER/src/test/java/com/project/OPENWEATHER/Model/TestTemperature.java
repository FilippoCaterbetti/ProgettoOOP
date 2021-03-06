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
		
		@BeforeEach
		void setUp() throws Exception {
			number = new Temperature("sunny","clouds",30,0,15,17,"05-03-2021");
		}

		
		@AfterEach
		void tearDown() throws Exception {
		}
		
		@Test
		void toJSONObject() {
		assertEquals("sunny", number.toJSONObject().get("main"));
		assertEquals("clouds", number.toJSONObject().get("description"));
		assertEquals(30D, number.toJSONObject().get("temp_max"));
		assertEquals(0D, number.toJSONObject().get("temp_min"));
		assertEquals(15D, number.toJSONObject().get("feels_like"));
		assertEquals(17D, number.toJSONObject().get("temp"));
		assertEquals("05-03-2021", number.toJSONObject().get("data"));
	}
		
}
