package com.project.OPENWEATHER.StatsAndFilters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.project.OPENWEATHER.error.ErrorCalculator;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.service.ServiceApplication;

public class Statistics extends ErrorCalculator {

	ServiceApplication service = new ServiceApplication();

	/**
	 * Questo metodo calcola la media di ogni temperatura e la varianza della
	 * temperatura reale e percepita di un giorno
	 * 
	 * @param next è il nome della città
	 * @return un JSONObject con tutte le medie delle temperature della città
	 *         richiesta
	 * @throws ParseException        per errore di parsing
	 * @throws IOException           per errore di input ed output
	 * @throws MalformedURLException per errore di formazione di URL
	 */

	public JSONObject todayAvg(String next) throws MalformedURLException, IOException, ParseException {

		City city = new City(next);

		city = service.getTempFutureApi(next);

		double temp_max_avg = 0;
		double temp_min_avg = 0;
		double feels_like_avg = 0;
		double real_temp_avg = 0;

		double variancefeelslike = 0;
		double variancetemp = 0;

		int i = 0;

		String date = "";
		date += (city.getTemps().get(0).getData()).charAt(8);
		date += (city.getTemps().get(0).getData()).charAt(9);

		String datas = date;

		double max_temp = 0;
		double min_temp = city.getTemps().get(i).getTemp();
		double max_feelslike = 0;
		double min_feelslike = city.getTemps().get(i).getFeels_like();

		while (date.equals(datas)) {

			temp_max_avg += city.getTemps().get(i).getTemp_max();
			temp_min_avg += city.getTemps().get(i).getTemp_min();
			feels_like_avg += city.getTemps().get(i).getFeels_like();
			real_temp_avg += city.getTemps().get(i).getTemp();

			if (city.getTemps().get(i).getTemp() > max_temp) {

				max_temp = city.getTemps().get(i).getTemp();

			}
			if (city.getTemps().get(i).getTemp() < min_temp) {

				min_temp = city.getTemps().get(i).getTemp();

			}
			if (city.getTemps().get(i).getFeels_like() > max_feelslike) {

				max_feelslike = city.getTemps().get(i).getFeels_like();

			}
			if (city.getTemps().get(i).getFeels_like() < min_feelslike) {

				min_feelslike = city.getTemps().get(i).getFeels_like();

			}
			i++;
			datas = "";
			datas += (city.getTemps().get(i).getData()).charAt(8);
			datas += (city.getTemps().get(i).getData()).charAt(9);
		}

		temp_max_avg = temp_max_avg / i;
		temp_min_avg = temp_min_avg / i;
		feels_like_avg = feels_like_avg / i;
		real_temp_avg = real_temp_avg / i;
		datas = date;
		i = 0;

		// calcolo della varianza di visibilità
		while (date.equals(datas)) {
			variancetemp = ((int) ((city.getTemps().get(i).getTemp()) - real_temp_avg)) ^ 2;
			variancefeelslike = ((int) ((city.getTemps().get(i).getFeels_like()) - feels_like_avg)) ^ 2;

			i++;
			datas = "";
			datas += (city.getTemps().get(i).getData()).charAt(8);
			datas += (city.getTemps().get(i).getData()).charAt(9);

		}

		variancetemp /= i;
		variancefeelslike /= i;

		JSONObject object = new JSONObject();
		JSONObject temp_data = new JSONObject();

		temp_data.put("Real temp average", real_temp_avg);
		temp_data.put("Max Real temp", max_temp);
		temp_data.put("Min Real temp", min_temp);
		temp_data.put("Real temp variance", variancetemp);

		temp_data.put("Feels like average", feels_like_avg);
		temp_data.put("Max feels like", max_feelslike);
		temp_data.put("Min feels like", min_feelslike);
		temp_data.put("Feels like variance", variancefeelslike);

		object.put("City", next);
		object.put("Temp_Max Average", temp_max_avg);
		object.put("Temp_Min Average", temp_min_avg);
		object.put("Real_temp Average", real_temp_avg);
		object.put("Feels_like Average", feels_like_avg);
		object.put("Temp and Feels like data", temp_data);
		return object;

	}

	/**
	 * Questo metodo calcola la media di ogni temperatura e la varianza della
	 * temperatura reale e percepita di 5 giorni
	 * 
	 * @param next è il nome della città
	 * @return un JSONObject con tutte le medie delle temperature della città
	 *         richiesta
	 * @throws ParseException        per errore di parsing
	 * @throws IOException           per errore di input ed output
	 * @throws MalformedURLException per errore di formazione di URL
	 */

	public JSONObject fiveDayAvg(String next) throws MalformedURLException, IOException, ParseException {
		City city = new City(next);

		city = service.getTempFutureApi(next);

		double temp_max_avg = 0;
		double temp_min_avg = 0;
		double feels_like_avg = 0;
		double real_temp_avg = 0;

		double variancefeelslike = 0;
		double variancetemp = 0;

		int i = 0;

		double max_temp = 0;
		double min_temp = city.getTemps().get(i).getTemp();
		double max_feelslike = 0;
		double min_feelslike = city.getTemps().get(i).getFeels_like();

		while (i < city.getTemps().size()) {

			temp_max_avg += city.getTemps().get(i).getTemp_max();
			temp_min_avg += city.getTemps().get(i).getTemp_min();
			feels_like_avg += city.getTemps().get(i).getFeels_like();
			real_temp_avg += city.getTemps().get(i).getTemp();

			if (city.getTemps().get(i).getTemp() > max_temp) {

				max_temp = city.getTemps().get(i).getTemp();

			}
			if (city.getTemps().get(i).getTemp() < min_temp) {

				min_temp = city.getTemps().get(i).getTemp();

			}
			if (city.getTemps().get(i).getFeels_like() > max_feelslike) {

				max_feelslike = city.getTemps().get(i).getFeels_like();

			}
			if (city.getTemps().get(i).getFeels_like() < min_feelslike) {

				min_feelslike = city.getTemps().get(i).getFeels_like();

			}
			i++;
		}

		temp_max_avg = temp_max_avg / i;
		temp_min_avg = temp_min_avg / i;
		feels_like_avg = feels_like_avg / i;
		real_temp_avg = real_temp_avg / i;

		i = 0;

		while (i < city.getTemps().size()) {

			variancetemp = ((int) ((city.getTemps().get(i).getTemp()) - real_temp_avg)) ^ 2;
			variancefeelslike = ((int) ((city.getTemps().get(i).getFeels_like()) - feels_like_avg)) ^ 2;

			i++;
		}

		variancetemp /= i;
		variancefeelslike /= i;

		JSONObject object = new JSONObject();
		JSONObject temp_data = new JSONObject();

		temp_data.put("Real temp average", real_temp_avg);
		temp_data.put("Max Real temp", max_temp);
		temp_data.put("Min Real temp", min_temp);
		temp_data.put("Real temp variance", variancetemp);

		temp_data.put("Feels like average", feels_like_avg);
		temp_data.put("Max feels like", max_feelslike);
		temp_data.put("Min feels like", min_feelslike);
		temp_data.put("Feels like variance", variancefeelslike);

		object.put("City", next);
		object.put("Temp_Max Average", temp_max_avg);
		object.put("Temp_Min Average", temp_min_avg);
		object.put("Real_temp Average", real_temp_avg);
		object.put("Feels_like Average", feels_like_avg);
		object.put("Temp and Feels like data", temp_data);
		return object;

	}

}
