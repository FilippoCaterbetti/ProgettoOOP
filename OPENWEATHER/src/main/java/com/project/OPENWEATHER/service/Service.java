package com.project.OPENWEATHER.service;


import java.io.IOException;
import java.util.ArrayList;


import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;

import org.json.JSONArray;
import org.json.JSONObject;

import com.project.OPENWEATHER.exception.InvalidStringException;
import com.project.OPENWEATHER.exception.NotAllowedPeriodException;
import com.project.OPENWEATHER.model.City;
// name indica la città desiderata


public interface Service {
	
	public String URLgenerator (String name);
	public abstract JSONObject getCityApi(String name);
	public abstract JSONArray getTempApi(String name);
	public abstract String save(String name) throws  IOException;
	public String FiveHoursInfo(String name);
	public String FiveDaysInfo(String name);
	//public abstract JSONArray getTempAPI(String name);
	public abstract JSONArray getTempFutureApi(String name);
	public abstract ArrayList<JSONObject> PeriodCity (String name, String period ) throws InvalidStringException, NotAllowedPeriodException;  //inserire vari errori 
	
	
}
