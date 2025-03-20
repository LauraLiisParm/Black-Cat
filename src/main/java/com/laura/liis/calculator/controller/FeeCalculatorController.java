package com.laura.liis.calculator.controller;

import com.laura.liis.calculator.dto.DeliveryFeeRequestDto;
import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.service.FeeService;
import com.laura.liis.calculator.service.WeatherDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeeCalculatorController {

    @Autowired
    private FeeService feeService;

    @GetMapping("/station/{station}")
    public WeatherDataEntity getWeatherData(@PathVariable String station) {
        return feeService.getWeatherDataByStation(station);
    }


    @GetMapping("/calculate")
    public double calculateDeliveryFee(@RequestParam String station, @RequestParam String vehicleType) {
        // Call the FeeService to calculate the total delivery fee
        return feeService.calculateDeliveryFee(station, vehicleType);
    }
}
