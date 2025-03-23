package com.laura.liis.calculator.job;

import com.laura.liis.calculator.client.WeatherStationClient;
import com.laura.liis.calculator.dto.ObservationsDto;
import com.laura.liis.calculator.service.WeatherDataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeatherDataUpdateJob {


    private final WeatherStationClient weatherStationClient;
    private final WeatherDataService weatherDataService;

    @PostConstruct
    public void runAtStartup() {
        log.error("Updating Weather data at startup");
        ObservationsDto weatherData = weatherStationClient.getWeatherData();
        log.info("Received weather data at startup: {}", weatherData);
        weatherDataService.updateWeatherData(weatherData);
    }

    @Scheduled(cron = "0 15 * * * * ")
    public void execute() {
        log.error("Updating Weather data");
        ObservationsDto weatherData = weatherStationClient.getWeatherData();
        log.info("Received weather data: {}", weatherData);
        weatherDataService.updateWeatherData(weatherData);
    }
}
