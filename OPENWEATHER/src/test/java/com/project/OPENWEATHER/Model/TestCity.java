package com.project.OPENWEATHER.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.OPENWEATHER.model.City;

class TestCity {

		private City citta1;
		private City citta2;
		
		@BeforeEach
		void setUp() throws Exception {
			citta1 = new City("Roma","Italy",1);
			citta2 = new City("Milano","Italy",2);
		}

		@AfterEach
		void tearDown() throws Exception {
		}

		
		
		@Test
		void testToJSONObject() {
			assertEquals("Roma", citta1.toJSONObject().get("name"));
			assertEquals("Italy", citta1.toJSONObject().get("country"));
			assertEquals(1L, citta1.toJSONObject().get("id"));
			}
		@Test
		void testClone() {
			assertEquals(citta1.toJSONObject(), citta1.clone().toJSONObject());
		}
}

