package com.laura.liis.calculator.service;

import com.laura.liis.calculator.dto.ObservationsDto;
import com.laura.liis.calculator.dto.StationDto;
import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.enums.City;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;

    @Transactional
    public void updateWeatherData(ObservationsDto observationsDto) {
        Instant timestamp = Instant.ofEpochMilli(observationsDto.getTimestamp());
        List<StationDto> stations = observationsDto.getStation() == null ? List.of() : observationsDto.getStation();

        List<StationDto> filteredStations = stations.stream()
                .filter(s -> isTallinnTartuOrParnu(s.getName()))
                .peek(stationDto -> stationDto.setName(City.fromCityName(stationDto.getName()).name()))
                .toList();


        filteredStations.forEach(station -> weatherDataRepository.findByStation(station.getName())
                .ifPresentOrElse(
                        entity -> updateExistingWeatherData(station, entity, timestamp),
                        () -> createNewWeatherDataEntry(station, timestamp)
                ));
    }

    private WeatherDataEntity createNewWeatherDataEntry(StationDto station, Instant timestamp) {
        return weatherDataRepository.save(WeatherDataEntity.builder()
                .station(station.getName())
                .phenomenon(station.getPhenomenon())
                .windspeed(station.getWindspeed())
                .wmocode(station.getWmocode())
                .airtemperature(station.getAirtemperature())
                .timestamp(timestamp)
                .build());
    }

    private void updateExistingWeatherData(StationDto station, WeatherDataEntity entity, Instant timestamp) {
        weatherDataRepository.save(entity.toBuilder()
                .station(station.getName())
                .phenomenon(station.getPhenomenon())
                .windspeed(station.getWindspeed())
                .wmocode(station.getWmocode())
                .airtemperature(station.getAirtemperature())
                .timestamp(timestamp)
                .build());
    }

    private static boolean isTallinnTartuOrParnu(String stationName) {
        return Arrays.stream(City.values())
                .anyMatch(city -> city.getCityName().equals(stationName));
    }

    private static WeatherDataEntity getWeatherDataEntity(StationDto stationData, Long timestamp) {
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
        weatherDataEntity.setStation(stationData.getName());  // Assuming "name" maps to "station"
        weatherDataEntity.setWmocode(stationData.getWmocode());
        weatherDataEntity.setAirtemperature(stationData.getAirtemperature());
        weatherDataEntity.setWindspeed(stationData.getWindspeed());
        weatherDataEntity.setPhenomenon(stationData.getPhenomenon());
        weatherDataEntity.setTimestamp(Instant.ofEpochMilli(timestamp));
        return weatherDataEntity;
    }

}

