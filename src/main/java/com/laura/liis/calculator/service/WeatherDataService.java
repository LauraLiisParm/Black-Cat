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
        try {
            // Fetch the data from the weather station
            ObservationsDto observations = weatherStationClient.getWeatherData();
            if (observations != null && observations.getStation() != null) {
                // Loop through each station and save the weather data
                for (StationDto stationData : observations.getStation()) {
                    WeatherDataEntity weatherDataEntity = getWeatherDataEntity(stationData);

                    // Save the weather data to the database using the repository
                    log.error("Saving weather data: ");
                    weatherDataRepository.save(weatherDataEntity);  // Save WeatherDataEntity object
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle any errors and log them
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

