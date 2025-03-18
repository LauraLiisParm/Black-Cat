package com.laura.liis.calculator.service;

import com.laura.liis.calculator.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherDataService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;


}
