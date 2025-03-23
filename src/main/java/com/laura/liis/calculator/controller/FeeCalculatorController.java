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
     *
     * @param request
     * @return
     */
    @GetMapping("/calculate")
    public double calculateDeliveryFee(@RequestParam String city, @RequestParam String vehicleType) {
        return feeService.calculateDeliveryFee(city, vehicleType);
    }
}
