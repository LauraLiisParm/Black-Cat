package com.laura.liis.calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeliveryFeeRequestDto {
    @JsonProperty("city")
    private String station;
    @JsonProperty("vehicleType")
    private String vehicleType;
}
