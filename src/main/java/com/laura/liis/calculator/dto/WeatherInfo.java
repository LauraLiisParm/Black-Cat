package com.laura.liis.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {
    private Long id;
    private String Station;
    private String wmoCode;
    private double airTemperature;
    private String windSpeed;
    private String phenomenon;
}
