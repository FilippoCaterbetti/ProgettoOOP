package com.project.OPENWEATHER.error;

import java.util.ArrayList;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorCalculator extends ResearchDay {

	public ErrorCalculator() {

	}

	/**
	 * Questo metodo serve per calcolare l'errore sulle predizioni di ogni città
	 * indicata nell'ArrayList. Chiamerà, infine, il metodo errorFilter di Errors
	 * responsabile del filtraggio della soglia di errore. Richiama il metodo
	 * ResearchDay della classe ResearchDay, che si occupa di calcolare il giorno e
	 * da cui partire per il calcolo.
	 * 
	 * @param cities   è l'ArrayList di Stringhe dei nomi delle città di cui si
	 *                 vuole conoscere la soglia di errore.
	 * @param tempInfo è l'ArrayList contenente le informazioni relative alla
	 *                 temperatura di ogni città indicata nell'ArrayList di Stringhe
	 *                 cities.
	 * @param error    è la soglia di errore su cui si vuole effettuare il
	 *                 filtraggio.
	 * @param value    è l'indicazione sul filtraggio.
	 * @param period   indica il numero di giorni su cui si vuole calcolare la
	 *                 soglia di errore.
	 * @return l'ArrayList di JSONObject contenente le informazioni sull'errore di
	 *         ogni città e, come ultimo elemento, il JSONObject contenente le città
	 *         che rispettano le condizioni indicate da error e value.
	 */
	public ArrayList<JSONObject> calculate(ArrayList<String> cities, ArrayList<JSONArray> tempInfo, int error,
			String value, int period) {

		ArrayList<JSONObject> tester = new ArrayList<JSONObject>();

		Iterator<JSONArray> tempInfoIt = tempInfo.iterator();
		Iterator<String> citiesIt = cities.iterator();
		String s = "";

		while (tempInfoIt.hasNext() && citiesIt.hasNext()) {

			int completeError = 0;
			int guessPrediction = 0;
			int cont = 0;

			JSONArray cityInfo = new JSONArray();
			cityInfo = tempInfoIt.next();

			// vado a prendere le informazioni sulle temperature solo della prima ora di
			// previsione
			JSONArray infoTempDayOne = new JSONArray();
			infoTempDayOne = cityInfo.getJSONArray(0); // ho tutte le previsioni della prima ora salvata nello storico

			JSONObject information = new JSONObject();
			information = researchDay(cityInfo, period);
			String startDay = information.getString("data");
			int startPosition = information.getInt("position");

			int endPosition = researchDay(cityInfo, period + 1).getInt("position");

			while (startPosition < endPosition) {

				for (int j = 0; j < cityInfo.getJSONArray(period).length(); j++) {

					JSONObject tempStats = new JSONObject();
					tempStats = cityInfo.getJSONArray(period).getJSONObject(j);

					if (startDay.equals(tempStats.getString("data"))) {

						double errore = (infoTempDayOne.getJSONObject(startPosition).getDouble("temp")
								- tempStats.getDouble("temp"));

						if (errore == 0) {
							guessPrediction++;
						}
						completeError += errore;
						cont++;
					}
					s = startDay;
				}

				startPosition++;

				startDay = s;

			}
			try {

				completeError /= cont;
			} catch (ArithmeticException e) {

				e.printStackTrace();
			}

			JSONObject errorInfo = new JSONObject();
			errorInfo.put("errori testati ", completeError);
			errorInfo.put("previsioni azzeccate su " + cont, guessPrediction);
			errorInfo.put("Città", citiesIt.next());
			tester.add(errorInfo);

		}

		Errors filter = new Errors();

		tester = filter.ErrorFilter(tester, error, value);

		return tester;
	}

}
