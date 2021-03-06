package com.project.OPENWEATHER.error;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class ErrorCalculator extends ResearchDay {
	
	public ErrorCalculator() {
		
	}
	/**
	 * Questo metodo serve per calcolare l'errore sulle predizioni di ogni città indicata nell'ArrayList. Chiamerà, infine,
	 * il metodo errorFilter di Errors responsabile del filtraggio della soglia di errore. Richiama il metodo
	 * findDay della classe FindDay, che si occupa di calcolare il giorno e  da cui partire per il calcolo.
	 * 
	 * @param cities è l'ArrayList di Stringhe dei nomi delle città di cui si vuole conoscere la soglia di errore.
	 * @param tempInfo è l'ArrayList contenente le informazioni relative alla temperatura di ogni città indicata 
	 *        nell'ArrayList di Stringhe cities.
	 * @param error è la soglia di errore su cui si vuole effettuare il filtraggio.
	 * @param value è l'indicazione sul filtraggio.
	 * @param period indica il numero di giorni su cui si vuole calcolare la soglia di errore.
	 * @return l'ArrayList di JSONObject contenente le informazioni sull'errore di ogni città e, come ultimo elemento,
	 *         il JSONObject contenente le città che rispettano le condizioni indicate da error e value.
	 */
	public ArrayList<JSONObject> calculate(ArrayList<String> cities,ArrayList<JSONArray> tempInfo,int error,String value,int period) {
		
		ArrayList<JSONObject> acceptableCities = new ArrayList<JSONObject> ();
		
		Iterator<JSONArray> tempInfoIt = tempInfo.iterator();
		Iterator<String> citiesIt = cities.iterator();
		
		while(tempInfoIt.hasNext() && citiesIt.hasNext()) {
			
			int errortot = 0;
			int azzeccate = 0;
			int contatore = 0;
			
			//mettiamo le informazioni della città che si trova al posto i
			JSONArray CityInfo = new JSONArray();
			CityInfo = visibilityIt.next();
			
			//vado a prendere le informazioni sulla visibilità solo del primo giorno di previsione
			JSONArray app = new JSONArray();
			app = CityInfo.getJSONArray(0); //ho tutto il primo giorno di visibilità
			
			
			JSONObject information = new JSONObject();
			information = researchDay(CityInfo,period);
		    String dataInizio = information.getString("date");
		    int startPosition = information.getInt("position");
		    int endPosition = researchDay(CityInfo,period+1).getInt("position");
		    
		    while(startPosition<endPosition) {
		    	
		    	for(int k=0; k<CityInfo.getJSONArray(period).length();k++) {
		    		
		    		JSONObject visibility = new JSONObject();
		    		visibility = CityInfo.getJSONArray(period).getJSONObject(k);
		    		
		    		if(dataInizio.equals(visibility.getString("data"))) {
		    			int errore;
		    			errore = (app.getJSONObject(startPosition).getInt("visibility")-visibility.getInt("visibility"));
		    			if(errore==0)
		    				azzeccate++;
		    			errortot+=errore;
		    			contatore++;
		    		}
		    		
		    	}
		    	startPosition++;
		    	dataInizio = app.getJSONObject(startPosition).getString("data");
		    
		    }
		  try {
		    errortot/=contatore;
		  }
		  catch (ArithmeticException e) {
			  e.printStackTrace();
		  }
            JSONObject errorInfo = new JSONObject();
            errorInfo.put("error AME", errortot);
            errorInfo.put("previsioni azzeccate su "+ contatore, azzeccate);
            errorInfo.put("City", citiesIt.next());
            acceptableCities.add(errorInfo);
           
           
        }
       
        Errors filter = new Errors();
       
        acceptableCities = filter.ErrorFilter(ret,error,value);
		
		return acceptableCities;
	}

	
	

}
