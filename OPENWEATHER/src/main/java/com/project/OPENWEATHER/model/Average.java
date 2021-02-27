package com.project.OPENWEATHER.model;

//Average serve per calcolare la temperatura media

public class Average {
	
	double avg;
	
	public Average(double max, double min) {
		avg = (max+min)/2; 
	}

	/**
	 * @return the avg
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * @param avg the avg to set
	 */
	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	
}
