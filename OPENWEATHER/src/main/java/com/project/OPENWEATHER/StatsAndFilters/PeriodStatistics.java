package com.project.OPENWEATHER.StatsAndFilters;

import org.json.JSONArray;

import org.json.JSONObject;

public class PeriodStatistics {

		/**
		 * Questo metodo restituisce il JSONArray con le statistiche sulle temperature di tutti i giorni presenti nello storico.
		 * @param city rappresenta il nome della città di cui si vuole fare statistica.
		 * @param temp contiene tutte le informazioni sulla temperatura presenti nello storico.
		 * @return un JSONArray con le statistiche di ogni giorno
		 */
		public JSONArray DailyStats(String city, JSONArray temp) {
			
			JSONArray stats = new JSONArray();
			int i=0;
			String date = "";
			stats.put(city);
			JSONObject open = new JSONObject(temp);
			JSONObject obj = new JSONObject();
			obj = open.getJSONObject("OpenWeather");	
			//JSONObject sp = new JSONObject();
			
			while(i < temp.length()) {
				
			    for(int k = 0; k < open.length(); k++) {	
			    		
				JSONObject oneDayVisibility = new JSONObject();
				
				oneDayVisibility += ;
				
			    }
				
				//preimpostiamo dei valori predefiniti di max min affinchè sia più semplice trovare i relativi max e min

				double average=0;
				int j=0;
				double max=0;
				double min=10000;
				double variance=0;
				
				while(j<oneDayVisibility.length()) {
					
					JSONObject hour = new JSONObject();
					hour = oneDayVisibility.getJSONObject(j);
					average += hour.getDouble("temp");
					date = hour.getString("data").substring(0,10);
					
					if(hour.getDouble("temp") > max) {
						
						max = hour.getDouble("temp");

					}
					
					if(hour.getDouble("temp") < min) {
						
						min = hour.getDouble("temp");

					}
					
					j++;
				}
				
				average = average/j;
				
				JSONObject info = new JSONObject();
				info.put("date", date);
				info.put("max", max);
				info.put("min", min);
				info.put("average", average);
				
				int k = 0;
				while(k < oneDayVisibility.length()) {
					
					JSONObject hour = new JSONObject();
					hour = oneDayVisibility.getJSONObject(k);
					hour.getDouble("temp");
			        variance += ((hour.getDouble("temp")-average)*(hour.getDouble("temp")-average));
			       	k++;
			     }
				
				variance /= j;
				info.put("variance", variance);
				
				stats.put(info);
				i++;
			}
			
			return stats;
		}
		
		/*
		 * 
		 * JSONArray stats = new JSONArray();
			int i=0;
			String date = "";
			stats.put(city);
			
			while(i < temp.length()) {
				
				JSONArray oneDayVisibility = new JSONArray();
				
				oneDayVisibility = temp.getJSONArray(i);
				
		 * 
		 */
		
		
		
		/**
		 * Questo metodo restituisce il JSONArray con le statistiche sulle temperature di ogni settimana presente nello storico.
		 * @param city rappresenta il nome della città di cui si vuole fare statistica.
		 * @param temp contiene tutte le informazioni sulla temperatura presenti nello storico.
		 * @return un JSONArray con le statistiche di ogni settimana
		 */
		public JSONArray  OneWeekStats(String city, JSONArray temp) {
			
			
			JSONArray oneDay = DailyStats(city,temp);
			JSONArray stats = new JSONArray();
			
			
			int i = 0;
			int k = 1;
			int week = 1;
			stats.put(oneDay.get(0));
			
			while(i < 21) {

				double average = 0;
				double max = 0;
				double min = 10000;
				double variance = 0;
				
				while(k < (8 + i) ) {
					
						JSONObject dayInfo = new JSONObject();
						dayInfo = oneDay.getJSONObject(k);
						average += dayInfo.getDouble("average");
						
						if(dayInfo.getDouble("max") > max) {
							
							max = dayInfo.getDouble("max");

						}
						
						if(dayInfo.getDouble("min") < min) {
							
							min = dayInfo.getDouble("min");

						}
						
						variance += dayInfo.getDouble("variance");	
						k++;
					
				}
				
				/**
				 * 
				 * Facciamo il tutto diviso 7 in quanto in una settimana ci sono 7 giorni
				 * e utilizziamo il metodo precedente in cui troviamo le statistiche di ogni giorno della settimana
				 */	
				
				average /= 7;
				variance /= 7;
				
				JSONObject weekInfo = new JSONObject();
				weekInfo.put("week",week);
				weekInfo.put("max", max);
				weekInfo.put("min", min);
				weekInfo.put("average", average);
				weekInfo.put("variance", variance);
				
				stats.put(weekInfo);
				
				
				week++;
				i += 7;
			
			}
			
			return stats;
			
		}
		
		
		/**
		 * Questo metodo restituisce il JSONArray con le statistiche sulle temperature del mese presenti nello storico.
		 * @param city rappresenta il nome della città di cui si vuole fare statistica.
		 * @param temp contiene tutte le informazioni sulla temperatura presenti nello storico.
		 * @return un JSONArray con le statistiche di un mese
		 */
		public JSONArray  OneMonthStats(String city, JSONArray temp) {
			
			JSONArray stats = new JSONArray();
			JSONArray oneWeek = new JSONArray();
			oneWeek = OneWeekStats(city, temp);
			
			stats.put(oneWeek.get(0));
						
			double average = 0;
			double max = 0;
			double min = 10000;
			double variance = 0;
			
			int i = 1;
			while(i < oneWeek.length()) {
				
				JSONObject info = new JSONObject();
				info = oneWeek.getJSONObject(i);
				average += info.getDouble("average");
				variance += info.getDouble("variance");
				
				if(info.getDouble("max") > max) {
					
					max = info.getDouble("max");

				}
				
				if(info.getDouble("min") < min) {
					
					min = info.getDouble("min");

				}
				i++;
					
			}
			
			/**
			 * 
			 * Facciamo il tutto diviso 4 in quanto in un mese ci sono 4 settimane 
			 * e utilizziamo il metodo precedente in cui troviamo le statistiche di ogni settimana del mese
			 */
			
			average /= 4;
			variance /= 4;
			
			JSONObject visibilityInfo = new JSONObject();
			visibilityInfo.put("average", average);
			visibilityInfo.put("max", max);
			visibilityInfo.put("min", min);
			visibilityInfo.put("variance", variance);
			
			stats.put(visibilityInfo);
			
			return stats;
			
		}
	}
