package com.laura.liis.calculator.job;

import com.laura.liis.calculator.client.WeatherStationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeatherDataUpdateJob {


    private final WeatherStationClient weatherStationClient;

    //@Scheduled(cron = "0 15 * * * * ")
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void execute() {
        log.error("Job runs");
        weatherStationClient.getWeatherData();
    }




}
