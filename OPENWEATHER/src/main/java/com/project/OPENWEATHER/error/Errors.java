package com.project.OPENWEATHER.error;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 
 * Questa classe contiene il metodo necessario a filtrare
 *  le città in base alla soglia di errore e al valore.
 *
 */
public class Errors{

	/**
	 * Costruttore della classe
	 */
		public Errors() {
		
	}
	
	/** 
	* Questo metodo filtra le città in base alla soglia di errore che l'utente inserisce 
	* in base alla stringa value, il metodo troverà le città che hanno errore maggiore, 
	* minore o uguale alla soglia di errore inserita dall'utente.
	* 
	* @param cityErrors ArrayList di JSONObject, ciascuno dei quali contiene il nome delle città e il relativo errore.
	* @param error rappresenta la soglia di errore immessa dall'utente.
	* @param value può assumere i valori "&gt","&lt" e "=" a seconda che l'utente voglia sapere quali città abbiano un
	*        errore maggiore, minore o uguale 
	*/
		
	public ArrayList<JSONObject> ErrorFilter(ArrayList<JSONObject> cityErrors, int error, String value) {
		
		String cities = "";
		
		if(value.equals("$gt")) {
			
			for(int i=0; i < cityErrors.size(); i++) {
				
				JSONObject cityInfo = new JSONObject();
				cityInfo = cityErrors.get(i);
				int cityError = cityInfo.getInt("error");
				
				if (cityError > error) {
					cities += cityInfo.getString("Città") + " ";
				}
			}
			
			JSONObject max = new JSONObject();
			max.put("maggiore" + error, cities);
			cityErrors.add(max);	
		}
		
		else if(value.equals("$lt")) {
				
			for(int i=0;i < cityErrors.size();i++) {
				
				JSONObject cityInfo = new JSONObject();
				cityInfo = cityErrors.get(i);
				int cityError = cityInfo.getInt("error");
				
				if (cityError < error) {
					cities += cityInfo.getString("Città") + " ";
				}
			}
			
			JSONObject min = new JSONObject();
			min.put("minore"+ error, cities);
			cityErrors.add(min);//associa alla città individuata il valore min	
		  }
		
		else if(value.equals("=")) {
				
			for(int i=0;i < cityErrors.size(); i++) {
				
				JSONObject cityInfo = new JSONObject();
				cityInfo = cityErrors.get(i);
				int cityError = cityInfo.getJSONObject("node").getString("nodeID");
				
				if (cityError == error) {
					cities += cityInfo.getString("Città")+ " ";
				}
			}
			
			JSONObject similar = new JSONObject();
			similar.put("uguale"+ error,cities);
			cityErrors.add(similar);
				
		   }
	    return cityErrors;
	   }
   }
	