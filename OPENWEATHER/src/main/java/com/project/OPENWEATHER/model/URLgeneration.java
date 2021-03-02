package com.project.OPENWEATHER.model;

public class URLgeneration {
	String url;
	String ApiKey;
	
	
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return ApiKey;
	}
	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.ApiKey = apiKey;
	}
	/**
	 * @param url
	 */
	public URLgeneration() {
		super();
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	
}
