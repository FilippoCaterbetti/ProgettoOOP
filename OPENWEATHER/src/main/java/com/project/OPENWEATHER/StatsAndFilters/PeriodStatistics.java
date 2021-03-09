package com.project.OPENWEATHER.StatsAndFilters;

import org.json.JSONArray;
import org.json.JSONObject;

public class PeriodStatistics {

		/**
		 * Questo metodo restituisce il JSONArray con le statistiche sulle temperature di tutti i giorni presenti nello storico.
		 * @param city rappresenta il nome della città di cui si vuole fare statistica.
		 * @param temp contiene tutte le informazioni sulla visibilità presenti nello storico.
		 * @return un JSONArray con le statistiche giorno per giorno.
		 */
		public JSONArray DailyStats(String city, JSONArray temp) {
			
			JSONArray stats = new JSONArray();
			
			String date = "";
			
			stats.put(city);
			
			for(int i = 0;i < temp.length(); i++) {
				
				JSONArray oneDayVisibility = new JSONArray();
				oneDayVisibility = temp.getJSONArray(i);
				
				int average=0;
				int j=0;
				int max=0;
				int min=10000;
				int variance=0;
				
				while(j<oneDayVisibility.length()) {
					
					JSONObject hour = new JSONObject();
					hour = oneDayVisibility.getJSONObject(j);
					average += hour.getInt("temperature");
					date = hour.getString("data").substring(0,10);
					
					if(hour.getInt("temperature") > max)
						max = hour.getInt("temperature");
					
					if(hour.getInt("temperature") < min)
						min = hour.getInt("temperature");
					
					j++;
				}
				
				average = average/j;
				
				JSONObject info = new JSONObject();
				info.put("date", date);
				info.put("max", max);
				info.put("min", min);
				info.put("average", average);
				
				while(j < oneDayVisibility.length()) {
					
					JSONObject hour = new JSONObject();
					hour = oneDayVisibility.getJSONObject(j);
					hour.getInt("temperature");
			        variance += ((hour.getInt("temperature")-average))^2;
			       	j++;
			     }
				
				variance /= j;
				info.put("variance", variance);
				
				stats.put(info);
			
			}
			
			return stats;
		}
		
		
		/**
		 * Questo metodo restituisce il JSONArray con le statistiche sulle temperature di ogni settimana presente nello storico.
		 * Richiama il metodo che va a calcolare la statistica giorno per giorno.
		 * @param city rappresenta il nome della città di cui si vuole fare statistica.
		 * @param temp contiene tutte le informazioni sulla visibilità presenti nello storico.
		 * @return un JSONArray con le statistiche settimana per settimana.
		 */
		public JSONArray  OneWeekStats(String city, JSONArray temp) {
			
			
			JSONArray oneDay = DailyStats(city,temp);
			JSONArray stats = new JSONArray();
			
			
			int i = 0;
			int k = 1;
			int week = 1;
			stats.put(oneDay.get(0));
			
			while(i < 21) {

				int average = 0;
				int max = 0;
				int min = 10000;
				int variance = 0;
				
				while(k < (8 + i) ) {
					
						JSONObject dayInfo = new JSONObject();
						dayInfo = oneDay.getJSONObject(k);
						average += dayInfo.getInt("average");
						
						if(dayInfo.getInt("max") > max)
							max = dayInfo.getInt("max");
						
						if(dayInfo.getInt("min") < min)
							min = dayInfo.getInt("min");
						
						variance += dayInfo.getInt("variance");	
						k++;
					
				}
				
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
		 * Richiama il metodo che va a calcolare la statistica settimana per settimana.
		 * @param city rappresenta il nome della città di cui si vuole fare statistica.
		 * @param visibility contiene tutte le informazioni sulla visibilità presenti nello storico.
		 * @return un JSONArray con le statistiche per tre settimane.
		 */
		public JSONArray  OneMonthStats(String city, JSONArray temp) {
			
			JSONArray stats = new JSONArray();
			JSONArray oneWeek = new JSONArray();
			oneWeek = OneWeekStats(city, temp);
			
			stats.put(oneWeek.get(0));
			
			int average = 0;
			int max = 0;
			int min = 10000;
			int variance = 0;
			
			int i = 1;
			while(i < oneWeek.length()) {
				
				JSONObject info = new JSONObject();
				info = oneWeek.getJSONObject(i);
				average += info.getInt("average");
				variance += info.getInt("variance");
				
				if(info.getInt("max") > max)
					max = info.getInt("max");
				
				if(info.getInt("min") < min)
					min = info.getInt("min");
				i++;
					
			}
			
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
