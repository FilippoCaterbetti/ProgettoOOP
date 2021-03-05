package com.project.OPENWEATHER.StatsAndFilters;

import java.util.Vector;

import org.json.JSONObject;

import com.project.OPENWEATHER.error.ErrorCalculator;
import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.model.City;
import com.project.OPENWEATHER.model.Temperature;
import com.project.OPENWEATHER.service.ServiceApplication;

public class Statistics extends ErrorCalculator{
	ServiceApplication service = new ServiceApplication();
	
	public JSONObject todayAvg(String next) {
	// TODO Auto-generated method stub
			return null;
	}

	public JSONObject fiveDayAvg(String next) {
		// TODO Auto-generated method stub
		return null;
	}
	public JSONObject weekAvg(String next) {
		
		return null;
	}
	
	public JSONObject monthAvg(String next) {
		return null;
	}
	
	
	}
