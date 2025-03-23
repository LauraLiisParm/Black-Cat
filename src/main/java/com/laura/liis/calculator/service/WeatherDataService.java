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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeatherDataService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private WeatherStationClient weatherStationClient;

    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    @Transactional
    public void updateWeatherData() {
        log.info("Scheduled task triggered");
        try {
            // Fetch the data from the weather station
            ObservationsDto observations = weatherStationClient.getWeatherData();
            log.info("Received weather data: {}", observations);

            if (observations != null && observations.getStation() != null) {
                // Define the filter criteria (e.g., a list of station names you want to include)
                List<String> selectedStationNames = List.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");  // Example list of station names you want to include

                // Loop through each station, but only process the selected stations
                for (StationDto stationData : observations.getStation()) {
                    // Check if the station name is in the selected list

                    /*
                    vaatad dbsse, kas selle linna kohta on andmed olemas
                    kui andmed olemas, siis uuendad olemasolevat kirjet
                    kui andmeid pole, siis tekitad uue kirje
                     */

                    weatherDataRepository.findByStation(stationData.getName())
                            .ifPresentOrElse(
                                    entity -> {
                                        entity.setAirtemperature(stationData.getAirtemperature());
                                        entity.setWmocode(stationData.getWmocode());
                                        entity.setWindspeed(stationData.getWindspeed());
                                        entity.setPhenomenon(stationData.getPhenomenon());
                                        weatherDataRepository.save(entity);
                                    },
                                    () -> weatherDataRepository.save(getWeatherDataEntity(stationData))
                            );
                    /*
                    if (selectedStationNames.contains(stationData.getName())) {
                        WeatherDataEntity weatherDataEntity = getWeatherDataEntity(stationData);

                        // Save the weather data to the database using the repository
                        weatherDataRepository.save(weatherDataEntity);
                        log.info("Saving Weather Data: {}", weatherDataEntity);
                    }
                    */
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

