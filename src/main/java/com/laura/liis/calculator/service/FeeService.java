package com.laura.liis.calculator.service;

import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.enums.City;
import com.laura.liis.calculator.exception.UnsupportedCityException;
import com.laura.liis.calculator.exception.UsageOfSelectedVehicleIsForbiddenException;
import com.laura.liis.calculator.exception.UnsupportedVehicleTypeException;
import com.laura.liis.calculator.exception.WeatherDataNotFoundException;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeeService {

    private final WeatherDataRepository weatherDataRepository;


    private double calculateRegionalBaseFee(String cityName, String vehicleType) {
         return switch (getCity(cityName)) {
            case TALLINN -> calculateBaseFeeForTallinn(vehicleType);
            case TARTU -> calculateBaseFeeForTartu(vehicleType);
            case PÄRNU -> calculateBaseFeeForPärnu(vehicleType);
        };
    }

    private static City getCity(String cityName) {
        City city;
        try {
            city = City.valueOf(cityName);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedCityException("City is not supported! City = " + cityName);
        }
        return city;
    }

    private static double calculateBaseFeeForPärnu(String vehicleType) {

        double regionalBaseFee;
        if ("CAR".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.0;
        } else if ("SCOOTER".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 2.5;
        } else if ("BIKE".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 2.0;
        } else {
            throw new UnsupportedVehicleTypeException("This vehicle type is not supported: " + vehicleType);
        }
        return regionalBaseFee;
    }

    private static double calculateBaseFeeForTartu(String vehicleType) {
        double regionalBaseFee;
        if ("CAR".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.5;
        } else if ("SCOOTER".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.0;
        } else if ("BIKE".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 2.5;
        } else {
            throw new UnsupportedVehicleTypeException("This vehicle type is not supported: " + vehicleType);
        }
        return regionalBaseFee;
    }

    private static double calculateBaseFeeForTallinn(String vehicleType) {
        double regionalBaseFee;
        if ("CAR".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 4.0;
        } else if ("SCOOTER".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.5;
        } else if ("BIKE".equalsIgnoreCase(vehicleType)) {
            regionalBaseFee = 3.0;
        } else {
            throw new UnsupportedVehicleTypeException("This vehicle type is not supported: " + vehicleType);
        }
        return regionalBaseFee;
    }

    private WeatherDataEntity fetchWeatherDataForStation(String stationName) {
        Optional<WeatherDataEntity> data = weatherDataRepository.findByStation(stationName);

        if (data.isEmpty()) {
            throw new WeatherDataNotFoundException("No weather data found for " + stationName);
        }
        return data.get();
    }

    private double calculateTemperatureFee(String vehicleType, WeatherDataEntity weatherDataEntity) {
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

    private double calculateWindSpeedFee(String vehicleType, WeatherDataEntity weatherDataEntity) {
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

    private double calculateWeatherPhenomenonFee(String vehicleType, WeatherDataEntity weatherDataEntity) {
        String phenomenon = weatherDataEntity.getPhenomenon();

        if ("SCOOTER".equalsIgnoreCase(vehicleType) || "BIKE".equalsIgnoreCase(vehicleType)) {

            if (phenomenon.toLowerCase().contains("snow") || phenomenon.toLowerCase().contains("sleet")) {
                return 1.0;
            } else if (phenomenon.toLowerCase().contains("rain") || phenomenon.toLowerCase().contains("shower")) {
                return 0.5;
            } else if (phenomenon.toLowerCase().contains("glaze") || phenomenon.toLowerCase().contains("hail") || phenomenon.toLowerCase().contains("thunder")) {
                throw new UsageOfSelectedVehicleIsForbiddenException("Usage of selected vehicle type is forbidden.");
            }
        }
        return 0.0;
    }

    public double calculateDeliveryFee(String city, String vehicleType) {
        return sumOfFees(city, vehicleType);
    }

    private double sumOfFees(String city, String vehicleType) {
        double regionalBaseFee = calculateRegionalBaseFee(city, vehicleType);
        WeatherDataEntity weatherDataEntity = fetchWeatherDataForStation(city);
        double airTemperatureFee = calculateTemperatureFee(vehicleType, weatherDataEntity);
        double windSpeedFee = calculateWindSpeedFee(vehicleType, weatherDataEntity);
        double weatherPhenomenonFee = calculateWeatherPhenomenonFee(vehicleType, weatherDataEntity);

        return regionalBaseFee + airTemperatureFee + windSpeedFee + weatherPhenomenonFee;
    }
}




