package com.project.OPENWEATHER.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OPENWEATHER.model.HelloWorldClass;

@RestController

public class simpleRestcontroller {
	
	@GetMapping("/hello")
	public HelloWorldClass exampleMethod(@RequestParam(name="param1", defaultValue="World") String param1)
	{
	
return new HelloWorldClass ("Filippo", "Caterbetti");
	}
}

