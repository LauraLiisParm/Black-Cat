package com.laura.liis.calculator.service;

import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.exception.WeatherDataNotFoundException;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeeService {

    private final WeatherDataRepository weatherDataRepository;

    private double calculateRegionalBaseFee(String station, String vehicleType) {
        double regionalBaseFee = 0.0;

        if (station.contains("Tallinn")) {
            //calculate for tallinn-Harku
        }
        switch (station) {
            case "Tallinn-Harku":
                if ("Car".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 4.0;
                } else if ("Scooter".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 3.5;
                } else if ("Bike".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 3.0;
                }
                break;
            case "Tartu-Tõravere":
                if ("Car".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 3.5;
                } else if ("Scooter".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 3.0;
                } else if ("Bike".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 2.5;
                }
                break;
            case "Pärnu":
                if ("Car".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 3.0;
                } else if ("Scooter".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 2.5;
                } else if ("Bike".equalsIgnoreCase(vehicleType)) {
                    regionalBaseFee = 2.0;
                }
                break;
            default:
                throw new RuntimeException("Unsupported city: " + station);
        }

        return regionalBaseFee;
    }

    private double calculateTemperatureFee(String station, String vehicleType) {
        WeatherDataEntity weatherData = weatherDataRepository.findByStation(station)
                .orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found for station = " + station));

        double airTemperature = weatherData.getAirtemperature();

        if ("Scooter".equalsIgnoreCase(vehicleType) || "Bike".equalsIgnoreCase(vehicleType)) {
            if (airTemperature < -10) {
                return 1.0;
            } else if (airTemperature >= -10 && airTemperature <= 0) {
                return 0.5;
            }
        }
        return 0.0;
    }

    private double calculateWindSpeedFee(String station, String vehicleType) {
        WeatherDataEntity weatherData = weatherDataRepository.findByStation(station)
                .orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found for station = " + station));

        double windSpeed = weatherData.getWindspeed();

        if ("Bike".equalsIgnoreCase(vehicleType)) {
            if (windSpeed > 20) {
                throw new RuntimeException("Usage of selected vehicle type is forbidden due to high wind speed.");
            } else if (windSpeed >= 10 && windSpeed <= 20) {
                return 0.5;
            }
        }
        return 0.0;
    }

    private double calculateWeatherPhenomenonFee(String station, String vehicleType) {
        WeatherDataEntity weatherData = weatherDataRepository.findByStation(station)
                .orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found for station = " + station));

        String phenomenon = weatherData.getPhenomenon();

        if ("Scooter".equalsIgnoreCase(vehicleType) || "Bike".equalsIgnoreCase(vehicleType)) {
            switch (phenomenon.toLowerCase()) {
                case "snow":
                case "sleet":
                    return 1.0;
                case "rain":
                    return 0.5;
                case "glaze":
                case "hail":
                case "thunder":
                    throw new RuntimeException("Usage of selected vehicle type is forbidden due to dangerous weather phenomenon.");
                default:
                    return 0.0;
            }
        }
        return 0.0;
    }

    public double calculateDeliveryFee(String station, String vehicleType) {
        double rbf = calculateRegionalBaseFee(station, vehicleType);
        double atef = calculateTemperatureFee(station, vehicleType);
        double wsef = calculateWindSpeedFee(station, vehicleType);
        double wpef = calculateWeatherPhenomenonFee(station, vehicleType);

        return rbf + atef + wsef + wpef;
    }

}




