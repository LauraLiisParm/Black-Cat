package com.laura.liis.calculator.controller;

import com.laura.liis.calculator.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeeCalculatorController {

    private final FeeService feeService;

    /// @param cityName, City where delivery needs to be done.
    /// @param vehicleType, Type of vehicle used for delivering.
    /// @return feeService.calculateDeliveryFee(city, vehicleType), returns calculated fee for specific city and vehicle type in current weather conditions.
    @GetMapping("/calculate")
    public double calculateDeliveryFee(@RequestParam String cityName, @RequestParam String vehicleType) {
        return feeService.calculateDeliveryFee(cityName, vehicleType);
    }
}
