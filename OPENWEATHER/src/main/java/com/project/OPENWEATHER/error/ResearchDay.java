package com.project.OPENWEATHER.error;

import org.json.JSONArray;

import org.json.JSONObject;

public class ResearchDay {
	
	public ResearchDay() {
		
	}
	/**
	 * Questo metodo viene richiamato dal metodo di Error Calculator per il calcolo dell'errore e si occupa di trovare il 
	 * giorno presente nello storico da cui partire o terminare (a seconda di quando viene richiamato)
	 * il calcolo dell'errore.
	 * 
	 * @param CityInfo è il JSONArray che contiene le informazioni generate a partire dall'avvio dello storico, sulle statistiche e sulle date di esse. 
	 * @param period rappresenta i giorni di predizione, che indicano il giorno da ricercare.
	 * @return il JSONObject che contiene la data ricercata e la posizione in cui si trova nel JSONArray.
	 */
    public JSONObject researchDay(JSONArray CityInfo, int period) {
		
		String date = "";
		
		JSONArray Day1 = new JSONArray();
		Day1 = CityInfo.getJSONArray(0);//tutte le informazioni del 1° giorno salvate nello storico
		JSONObject obj = new JSONObject();
		obj = Day1.getJSONObject(0);
		date = obj.getString("data");
		
		//firstDate = prende solo il giorno 'dd'
		String firstDate="";
		firstDate += date.charAt(8);
		firstDate += date.charAt(9);
		
		int i=0;
		
		// periodo da 1 a 5 (inclusi)
		for(int j = 0; j < period; j++) {
			
			boolean control = true;
			for(i=0; i < Day1.length() && control; i++) { //day1.length() = 4 blocchi di statistiche per 24 ore
			
				String dates = Day1.getJSONObject(i).getString("data");//scorre le date dello storico
				
				String justday = "";
				justday += dates.charAt(8);
				justday += dates.charAt(9);
				
				if(!justday.equals(firstDate)) {
					
					 control = false;		

				}
		}
			JSONObject obj1 = new JSONObject();
			obj1 = Day1.getJSONObject(i-1);//i-1(5-1) = 24 ore del primo giorno
			
			date = obj1.getString("data");
			
			firstDate="";
			firstDate += date.charAt(8);
			firstDate += date.charAt(9);
		
		}
		
		JSONObject info = new JSONObject();
		info.put("date", Day1.getJSONObject(i-1).getString("data"));
		info.put("position", i-1);//posizione che ha nell'array
		
		System.out.print(info);
		return info;
		
	}
}

