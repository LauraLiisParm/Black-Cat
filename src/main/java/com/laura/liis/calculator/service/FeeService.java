package com.laura.liis.calculator.service;

import com.laura.liis.calculator.dto.DeliveryFeeRequestDto;
import com.laura.liis.calculator.dto.ObservationsDto;
import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeeService {

    private final WeatherDataRepository weatherDataRepository;

    public double calculateRegionalBaseFee(String station, String vehicleType) {
        // Fetch weather data based on station
        double rbf = 0.0;

        // Business logic for Regional Base Fee (RBF) based on city and vehicle type

        switch (station) {
            case "Tallnn-Harku":
                if ("Car".equalsIgnoreCase(vehicleType)) {
                    rbf = 4.0;
                } else if ("Scooter".equalsIgnoreCase(vehicleType)) {
                    rbf = 3.5;
                } else if ("Bike".equalsIgnoreCase(vehicleType)) {
                    rbf = 3.0;
                }
                break;
            case "Tartu-Tõravere":
                if ("Car".equalsIgnoreCase(vehicleType)) {
                    rbf = 3.5;
                } else if ("Scooter".equalsIgnoreCase(vehicleType)) {
                    rbf = 3.0;
                } else if ("Bike".equalsIgnoreCase(vehicleType)) {
                    rbf = 2.5;
                }
                break;
            case "Pärnu":
                if ("Car".equalsIgnoreCase(vehicleType)) {
                    rbf = 3.0;
                } else if ("Scooter".equalsIgnoreCase(vehicleType)) {
                    rbf = 2.5;
                } else if ("Bike".equalsIgnoreCase(vehicleType)) {
                    rbf = 2.0;
                }
                break;
            default:
                throw new RuntimeException("Unsupported city: " + station);
        }

        return rbf;
    }

    // Method to calculate temperature fee (ATEF) based on air temperature
    public double calculateTemperatureFee(String station, String vehicleType) {
        // Fetch weather data from repository
        WeatherDataEntity weatherData = getWeatherDataByStation(station);

        double airTemperature = weatherData.getAirtemperature();

        if ("Scooter".equalsIgnoreCase(vehicleType) || "Bike".equalsIgnoreCase(vehicleType)) {
            if (airTemperature < -10) {
                return 1.0; // ATEF = 1 € for temperature < -10°C
            } else if (airTemperature >= -10 && airTemperature <= 0) {
                return 0.5; // ATEF = 0.5 € for temperature between -10°C and 0°C
            }
        }
        return 0.0; // No ATEF if conditions are not met
    }

    // Method to calculate wind speed fee (WSEF) based on wind speed
    public double calculateWindSpeedFee(String station, String vehicleType) {
        // Fetch weather data from repository
        WeatherDataEntity weatherData = getWeatherDataByStation(station);

        double windSpeed = weatherData.getWindspeed();

        if ("Bike".equalsIgnoreCase(vehicleType)) {
            if (windSpeed > 20) {
                throw new RuntimeException("Usage of selected vehicle type is forbidden due to high wind speed.");
            } else if (windSpeed >= 10 && windSpeed <= 20) {
                return 0.5; // WSEF = 0.5 € for wind speed between 10 and 20 m/s
            }
        }
        return 0.0; // No WSEF if conditions are not met
    }

    // Method to calculate weather phenomenon fee (WPEF) based on phenomenon
    public double calculateWeatherPhenomenonFee(String station, String vehicleType) {
        // Fetch weather data from repository
        WeatherDataEntity weatherData = getWeatherDataByStation(station);

        String phenomenon = weatherData.getPhenomenon();

        if ("Scooter".equalsIgnoreCase(vehicleType) || "Bike".equalsIgnoreCase(vehicleType)) {
            switch (phenomenon.toLowerCase()) {
                case "snow":
                case "sleet":
                    return 1.0; // WPEF = 1 € for snow or sleet
                case "rain":
                    return 0.5; // WPEF = 0.5 € for rain
                case "glaze":
                case "hail":
                case "thunder":
                    throw new RuntimeException("Usage of selected vehicle type is forbidden due to dangerous weather phenomenon.");
                default:
                    return 0.0; // No WPEF for other phenomena
            }
        }
        return 0.0; // No WPEF if conditions are not met
    }

    // Method to calculate the total delivery fee
    public double calculateDeliveryFee(String station, String vehicleType) {
        double rbf = calculateRegionalBaseFee(station, vehicleType);
        double atef = calculateTemperatureFee(station, vehicleType);
        double wsef = calculateWindSpeedFee(station, vehicleType);
        double wpef = calculateWeatherPhenomenonFee(station, vehicleType);

        // Total fee = RBF + ATEF + WSEF + WPEF
        return rbf + atef + wsef + wpef;
    }

    // Method to get weather data for the specified station
    public WeatherDataEntity getWeatherDataByStation(String station) {
        Optional<WeatherDataEntity> weatherData = weatherDataRepository.findByStation(station);

        // If data is found, return it; otherwise, handle the case when data is not found
        if (weatherData.isPresent()) {
            return weatherData.get();
        } else {
            throw new RuntimeException("Weather data for station " + station + " not found.");
        }
    }


}




