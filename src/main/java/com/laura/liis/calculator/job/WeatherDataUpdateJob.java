package com.laura.liis.calculator.job;

import com.laura.liis.calculator.client.WeatherStationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeatherDataUpdateJob {

    private final WeatherStationClient weatherStationClient;

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        log.error("Job runs");

        weatherStationClient.getWeatherData();
    }


}
