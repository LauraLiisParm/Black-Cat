package com.laura.liis.calculator.controller;

import com.laura.liis.calculator.service.WeatherDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeeCalculatorController {

    private WeatherDataService weatherDataService;

    @Autowired
    public void TestController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @GetMapping("/updateWeather")
    public String updateWeatherManually() {
        weatherDataService.updateWeatherData();  // Manually trigger the task
        return "Weather data updated successfully";
    }
}
