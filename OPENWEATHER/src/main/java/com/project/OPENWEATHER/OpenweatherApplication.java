package com.project.OPENWEATHER;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.OPENWEATHER.service.ServiceApplication;

@SpringBootApplication(scanBasePackages={"com.project.OPENWEATHER.controller", "com.project.OPENWEATHER.service","com.project.OPENWEATHER.model"})
public class OpenweatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenweatherApplication.class, args);
	
	}
}

