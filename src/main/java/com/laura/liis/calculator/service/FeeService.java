package com.laura.liis.calculator.service;

import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.enums.City;
import com.laura.liis.calculator.exception.UnsupportedCityException;
import com.laura.liis.calculator.exception.UsageOfSelectedVehicleIsForbiddenException;
import com.laura.liis.calculator.exception.WeatherDataNotFoundException;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeeService {

    private final WeatherDataRepository weatherDataRepository;


    private double calculateRegionalBaseFee(String city, String vehicleType) {
        double regionalBaseFee = 0.0;

        regionalBaseFee = switch (City.valueOf(city)) {
            case TALLINN -> calculateBaseFeeForTallinn(city, vehicleType, regionalBaseFee);
            case TARTU -> calculateBaseFeeForTartu(city, vehicleType, regionalBaseFee);
            case PÄRNU -> calculateBaseFeeForPärnu(city, vehicleType, regionalBaseFee);
            default -> throw new UnsupportedCityException("Unsupported city: " + city);
        };

        return regionalBaseFee;
    }

    private static double calculateBaseFeeForPärnu(String city, String vehicleType, double regionalBaseFee) {
        if ("CAR".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.0;
        } else if ("SCOOTER".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 2.5;
        } else if ("BIKE".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 2.0;
        }
        return regionalBaseFee;
    }

    private static double calculateBaseFeeForTartu(String city, String vehicleType, double regionalBaseFee) {
        if ("CAR".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.5;
        } else if ("SCOOTER".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.0;
        } else if ("BIKE".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 2.5;
        }
        return regionalBaseFee;
    }

    private static double calculateBaseFeeForTallinn(String city, String vehicleType, double regionalBaseFee) {
        if ("CAR".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 4.0;
        } else if ("SCOOTER".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.5;
        } else if ("BIKE".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.0;
        }
        return regionalBaseFee;
    }

    private WeatherDataEntity fetchWeatherDataForStation(String stationName) {
        Optional<WeatherDataEntity> data = weatherDataRepository.findByStation(stationName);

        if(data.isEmpty()){
            throw new WeatherDataNotFoundException("No weather data found for " + stationName);
        }
        return data.get();
    }

    private double calculateTemperatureFee(String city, String vehicleType) {

        WeatherDataEntity weatherDataEntity = fetchWeatherDataForStation(city);

        double airTemperature = weatherDataEntity.getAirtemperature();


        if ("SCOOTER".equalsIgnoreCase(vehicleType) || "BIKE".equalsIgnoreCase(vehicleType)) {
            if (airTemperature < -10) {
                return 1.0;
            } else if (airTemperature >= -10 && airTemperature <= 0) {
                return 0.5;
            }
        }
        return 0.0;
    }

    private double calculateWindSpeedFee(String city, String vehicleType) {
        WeatherDataEntity weatherDataEntity = weatherDataRepository.findByStation(city)
                .orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found for station = " + city));

        double windSpeed = weatherDataEntity.getWindspeed();

        if ("BIKE".equalsIgnoreCase(vehicleType)) {
            if (windSpeed > 20) {
                throw new UsageOfSelectedVehicleIsForbiddenException("Usage of selected vehicle type is forbidden.");
            } else if (windSpeed >= 10) {
                return 0.5;
            }
        }
        return 0.0;
    }

    private double calculateWeatherPhenomenonFee(String city, String vehicleType) {
        WeatherDataEntity weatherDataEntity = weatherDataRepository.findByStation(city)
                .orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found for station = " + city));

        String phenomenon = weatherDataEntity.getPhenomenon();

        if ("SCOOTER".equalsIgnoreCase(vehicleType) || "BIKE".equalsIgnoreCase(vehicleType)) {

            if (phenomenon.toLowerCase().contains("snow") || phenomenon.toLowerCase().contains("sleet")) {
                return 1.0;
            } else if (phenomenon.toLowerCase().contains("rain")) {
                return 0.5;
            } else if (phenomenon.toLowerCase().contains("glaze") || phenomenon.toLowerCase().contains("hail") || phenomenon.toLowerCase().contains("thunder")) {
                throw new UsageOfSelectedVehicleIsForbiddenException("Usage of selected vehicle type is forbidden.");
            }
        }
        return 0.0;
    }

    public double calculateDeliveryFee(String city, String vehicleType) {

        double regionalBaseFee = calculateRegionalBaseFee(city, vehicleType);
        double airTemperatureFee = calculateTemperatureFee(city, vehicleType);
        double windSpeedFee = calculateWindSpeedFee(city, vehicleType);
        double weatherPhenomenonFee = calculateWeatherPhenomenonFee(city, vehicleType);

        return regionalBaseFee + airTemperatureFee + windSpeedFee + weatherPhenomenonFee;
    }

}




