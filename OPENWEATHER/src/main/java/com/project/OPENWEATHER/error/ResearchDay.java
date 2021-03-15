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
		
		JSONObject Day1 = new JSONObject();
		Day1 = CityInfo.getJSONObject(0);     //tutte le informazioni della prima ora
		date = Day1.getString("data");
		
		//firstDate = prende solo il giorno 'dd'
		String firstDate="";
		firstDate += date.charAt(8);
		firstDate += date.charAt(9);
		
		int i=0;
		
		// periodo da 1 a 5 (inclusi)
		for(int j = 0; j < period; j++) {
			
			boolean control = true;
			for(i=0; i < CityInfo.length() && control; i++) { //day1.length() = 4 blocchi di statistiche per 24 ore
			
				String dates = CityInfo.getJSONObject(i).getString("data");//scorre le date dello storico
				
				String justday = "";
				justday += dates.charAt(8);
				justday += dates.charAt(9);
				
				if(!justday.equals(firstDate)) {
					
					 control = false;		
				}
		    }   
			// i è il valore che fa scattare al giorno successivo
		
			
			date = CityInfo.getJSONObject(i-1).getString("data");
			
			firstDate="";
			firstDate += date.charAt(8);
			firstDate += date.charAt(9);
		
		}
		
		i -=1;
		JSONObject info = new JSONObject();
		info.put("date", CityInfo.getJSONObject(i).getString("data"));
		info.put("position", i);   //posizione che ha nell'array
		
		//System.out.print(info);
		return info;
		
	}
}

