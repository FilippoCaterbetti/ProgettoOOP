package com.project.OPENWEATHER.model;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "coord" })
public class City { // jsonclass

	// Implementazione costruttori

	private String name;
	private String state;
	private String country;
	private long id;
	private Vector<Temperature> temps;

	public City() {
		super();
	}

	/**
	 * Costruttore per City
	 * 
	 * @param "name"    nome città
	 * @param "id"      id della città
	 * @param "country" stato dove si trova la città
	 * 
	 */
	public City(String name, String country, long id) {

		this.id = id;
		this.name = name;
		this.country = country;
		this.temps = new Vector<Temperature>();
	}

	public City(String name) {

		this.id = 0;
		this.name = name;
		this.country = null;
		this.temps = null;
	}

	public City(long id) {

		this.id = id;
		this.name = null;
		this.country = null;
		this.temps = null;
	}

	public City(String name, String country) {
		this.id = 0;
		this.name = name;
		this.country = country;
	}

	/**
	 * Costruttore per City
	 * 
	 * @param City JSONObject contenente: "name" nome città, "id" id della città,
	 *             "country" stato dove si trova la città ,"temps" JSONArray con le
	 *             temperature della città
	 */
	public City(JSONObject city) {

		this.name = city.get("name").toString(); // (String) city.get("name") o city.getString("name") forse
		this.id = (long) city.get("id"); // (long) city.get("id"); o city.getLong("id")
		this.country = city.get("country").toString();
		this.temps = new Vector<Temperature>();

		JSONArray temps = (JSONArray) city.get("temperature");
		for (int j = 0; j < temps.length(); j++) {

			this.temps.add(new Temperature((JSONObject) temps.get(j)));
		}
	}

	/**
	 * Metodo per aggiungere dati al vettore delle temperature della città
	 * 
	 * @param datoDaAgg dato Da Aggiungere
	 */

	public void aggiungi(Temperature datoDaAgg) {
		this.temps.add(datoDaAgg);
	}

	/**
	 * JSONObject per avere i dati della città
	 * 
	 * @return un JSONObject di dati complessivi della città
	 */

	public JSONObject toJSONObject() {

		JSONObject t = new JSONObject();
		t.put("name", this.name);
		t.put("country", this.country);
		t.put("id", this.id);

		JSONArray temps = new JSONArray();
		for (Temperature p : this.temps) {
			temps.put(p.toJSONObject());
		}
		t.put("temperature", temps);
		return t;
	}

	/**
	 * @return the temps
	 */
	public Vector<Temperature> getTemps() {
		return temps;
	}

	/**
	 * @param temps the temps to set
	 */
	public void setTemps(Vector<Temperature> temps) {
		this.temps = temps;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
