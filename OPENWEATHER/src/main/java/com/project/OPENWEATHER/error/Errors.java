package com.project.OPENWEATHER.error;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Errors{

/**
 * Costruttore della classe
 */
	public Errors() {
		
	}
	
/** 
* Questo metodo filtra le città in base alla soglia di errore che l'utente inserisce 
* In base alla stringa value, il metodo troverà le città che hanno errore maggiore, 
* minore o uguale alla soglia
* di errore inserita dall'utente.
* 
* @param errors ArrayList di JSONObject, ciascuno dei quali contiene il nome delle città e il relativo errore.
* @param error rappresenta la soglia di errore immessa dall'utente.
* @param value può assumere i valori "&gt","&lt" e "=" a seconda che l'utente voglia sapere quali città abbiano un
*        errore maggiore, minore o uguale 
*/
	
	public ArrayList<JSONObject> ErrorFilter(ArrayList<JSONObject> errors, int error, String value) {
		
		String names = "";
		
		if(value.equals("&gt")) {
			for(int i=0;i<errors.size();i++) {
				JSONObject cityInfo = new JSONObject();
				cityInfo = errors.get(i);
				int cityError = cityInfo.getInt("error");
				if (cityError>error)
					names+=cityInfo.getString("Città")+" ";
			}
			JSONObject max = new JSONObject();
			max.put("&gt"+error,names);
			errors.add(max);		
		}
		else if(value.equals("&lt")) {
			for(int i=0;i<errors.size();i++) {
				JSONObject cityInfo = new JSONObject();
				cityInfo = errors.get(i);
				int cityError = cityInfo.getInt("error");
				if (cityError<error)
					names+=cityInfo.getString("Città")+" ";
			}
			JSONObject max = new JSONObject();
			max.put("&lt"+error,names);
			errors.add(max);
		}
		else if(value.equals("=")) {
			for(int i=0;i<errors.size();i++) {
				JSONObject cityInfo = new JSONObject();
				cityInfo = errors.get(i);
				int cityError = cityInfo.getInt("error");
				if (cityError==error)
					names+=cityInfo.getString("Città")+" ";
			}
			JSONObject max = new JSONObject();
			max.put("="+error,names);
			errors.add(max);
				
		}
		
		return errors;
		
	}
	
}