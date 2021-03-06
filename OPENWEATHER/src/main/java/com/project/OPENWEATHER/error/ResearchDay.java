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
	 * @param cityVisibility è il JSONArray che contiene le informazioni sulla visibilità e sulle date della stessa.
	 * @param period rappresenta i giorni di predizione, che indicano il giorno da ricercare.
	 * @return il JSONObject che contiene la data ricercata e la posizione in cui si trova nel JSONArray.
	 */
    public JSONObject researchDay(JSONArray CityInfo, int period) {
		
		String date="";
		
		JSONArray Day1 = new JSONArray();
		Day1 = CityInfo.getJSONArray(0);
		JSONObject obj = new JSONObject();
		obj = Day1.getJSONObject(0);
		date = obj.getString("data");
		
		//formato date : 'dd-mm-yyyy'
		String firstDate="";
		firstDate += date.charAt(0);
		firstDate += date.charAt(1);
		
		int i=0;
		
		// periodo da 1 a 5 (inclusi)
		for(int j = 0; j < period; j++) {
			
			boolean control = true;
			for(i=0; i < Day1.length() && control; i++) {
			
				String dates = Day1.getJSONObject(i).getString("data");
				
				String justday = "";
				justday += dates.charAt(0);
				justday += dates.charAt(1);
				
				if(!justday.equals(firstDate))
					 control = false;		
		}
			JSONObject object = new JSONObject();
			object = Day1.getJSONObject(i-1);
			
			date = object.getString("data");
			
			firstDate="";
			firstDate += date.charAt(0);
			firstDate += date.charAt(1);
		
		}
		
		JSONObject info = new JSONObject();
		info.put("date", Day1.getJSONObject(i-1).getString("data"));
		info.put("position", i-1);
		
		System.out.print(info);
		return info;
		
	}
}

