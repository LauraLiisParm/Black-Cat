package com.laura.liis.calculator.controller;

import com.laura.liis.calculator.dto.DeliveryFeeRequestDto;
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
    public double calculateDeliveryFee(@RequestParam DeliveryFeeRequestDto request) {
        return feeService.calculateDeliveryFee(request.getStation(), request.getVehicleType());
    }
}
