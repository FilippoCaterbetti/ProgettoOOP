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
	 * il metodo errorFilter di ErrorFilter responsabile del filtraggio della soglia di errore. Richiama il metodo
	 * findDay della classe FindDay, che si occupa di calcolare il giorno e l'ora da cui partire per il calcolo.
	 * 
	 * @param cities è l'ArrayList di Stringhe contenenti i nomi delle città di cui si vuole conoscere la soglia di errore.
	 * @param visibilityInfo è l'ArrayList contenente le informazioni relative alla visibilità di ogni città indicata 
	 *        nell'ArrayList di Stringhe cities.
	 * @param error è la soglia di errore su cui si vuole effettuare il filtraggio.
	 * @param value è l'indicazione sul filtraggio.
	 * @param period indica il numero di giorni su cui si vuole calcolare la soglia di errore.
	 * @return l'ArrayList di JSONObject contenente le informazioni sull'errore di ogni città e, come ultimo elemento,
	 *         il JSONObject contenente le città che rispettano le condizioni indicate da error e value.
	 */
	public ArrayList<JSONObject> calculate(ArrayList<String> cities,ArrayList<JSONArray> visibilityInfo,int error,String value,int period) {
		
		ArrayList<JSONObject> ret = new ArrayList<JSONObject> ();
		
		Iterator<JSONArray> visibilityIt = visibilityInfo.iterator();
		Iterator<String> citiesIt = cities.iterator();
		
		while(visibilityIt.hasNext() && citiesIt.hasNext()) {
			
			int errortot = 0;
			int azzeccate = 0;
			int contatore = 0;
			
			//mettiamo le informazioni della città che si trova al posto i
			JSONArray cityVisibility = new JSONArray();
			cityVisibility = visibilityIt.next();
			
			//vado a prendere le informazioni sulla visibilità solo del primo giorno di previsione
			JSONArray app = new JSONArray();
			app = cityVisibility.getJSONArray(0); //ho tutto il primo giorno di visibilità
			
			
			JSONObject information = new JSONObject();
			information = researchDay(cityVisibility,period);
		    String dataInizio = information.getString("date");
		    int startPosition = information.getInt("position");
		    int endPosition = researchDay(cityVisibility,period+1).getInt("position");
		    
		    while(startPosition<endPosition) {
		    	
		    	for(int k=0; k<cityVisibility.getJSONArray(period).length();k++) {
		    		
		    		JSONObject visibility = new JSONObject();
		    		visibility = cityVisibility.getJSONArray(period).getJSONObject(k);
		    		
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
            errorInfo.put("previsioni azzeccate su "+contatore, azzeccate);
            errorInfo.put("City", citiesIt.next());
            ret.add(errorInfo);
           
           
        }
       
        Errors filter = new Errors();
       
        ret = filter.ErrorFilter(ret,error,value);
		
		return ret;
	}

	
	

}
