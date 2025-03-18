package com.laura.liis.calculator.service;

import com.laura.liis.calculator.client.WeatherStationClient;
import com.laura.liis.calculator.dto.ObservationsDto;
import com.laura.liis.calculator.dto.StationDto;
import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class WeatherDataService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private WeatherStationClient weatherStationClient;

    @Scheduled(fixedRate = 6000)
    @Transactional
    public void updateWeatherData() {
        log.info("Scheduled task triggered");
        try {
            // Fetch the data from the weather station
            ObservationsDto observations = weatherStationClient.getWeatherData();
            log.info("Received weather data: {}", weatherStationClient.getWeatherData());

            if (observations != null && observations.getStation() != null) {
                // Loop through each station and save the weather data
                for (StationDto stationData : observations.getStation()) {
                    WeatherDataEntity weatherDataEntity = getWeatherDataEntity(stationData);


                    // Save the weather data to the database using the repository
                    weatherDataRepository.save(weatherDataEntity);
                    log.info("Saving Weather Data: {}", weatherDataEntity);


                }
            }
        } catch (Exception e) {
            log.error("Error fetching weather data", e);
        }

    }

    private static WeatherDataEntity getWeatherDataEntity(StationDto stationData) {
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
        weatherDataEntity.setStation(stationData.getName());  // Assuming "name" maps to "station"
        weatherDataEntity.setWmocode(stationData.getWmocode());
        weatherDataEntity.setAirtemperature(stationData.getAirtemperature());
        weatherDataEntity.setWindspeed(stationData.getWindspeed());
        weatherDataEntity.setPhenomenon(stationData.getPhenomenon());

        return weatherDataEntity;
    }


}

