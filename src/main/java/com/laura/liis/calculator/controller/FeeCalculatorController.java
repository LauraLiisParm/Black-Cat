package com.laura.liis.calculator.controller;

import com.laura.liis.calculator.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeeCalculatorController {

    private final FeeService feeService;

    /**
     * Returns Delivery Fee based on city where delivery happens, vehicle used for delivery and current weather conditions in the specified city
     * @param cityName User input of a city where food is delivered
     * @param vehicleType user input of chosen vehicle
     * @return Fee for the food delivery
     */
    @GetMapping("/calculate")
    public double calculateDeliveryFee(@RequestParam String cityName, @RequestParam String vehicleType) {
        return feeService.calculateDeliveryFee(cityName, vehicleType);
    }
}