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
    public JSONObject researchDay(JSONArray cityVisibility, int period) {
		
		String date="";
		
		JSONArray firstDay = new JSONArray();
		firstDay = cityVisibility.getJSONArray(0);
		
		JSONObject obj = new JSONObject();
		obj = firstDay.getJSONObject(0);
		date = obj.getString("data");
		
		String firstDate="";
		firstDate += date.charAt(8);
		firstDate += date.charAt(9);
		
		int numero=0;
		int i=0;
		
		while(numero<period) {
			
			boolean control = true;
			while(i<firstDay.length() && control) {
			
				String alldate = firstDay.getJSONObject(i).getString("data");
				
				String justday = "";
				justday += alldate.charAt(8);
				justday += alldate.charAt(9);
				
				if(!justday.equals(firstDate))
					control = false;
			
				i++;
				
		}
			JSONObject obje = new JSONObject();
			obje = firstDay.getJSONObject(i-1);
			
			date = obje.getString("data");
			
			firstDate="";
			firstDate += date.charAt(8);
			firstDate += date.charAt(9);
			
			numero++;
		
		}
		
		JSONObject information = new JSONObject();
		information.put("date", firstDay.getJSONObject(i-1).getString("data"));
		information.put("position", i-1);
		
		System.out.print(information);
		
		return information;
		
		
	}

}

