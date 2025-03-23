package com.laura.liis.calculator.service;

import com.laura.liis.calculator.dto.DeliveryFeeRequestDto;
import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.enums.City;
import com.laura.liis.calculator.exception.WeatherDataNotFoundException;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.laura.liis.calculator.enums.City.*;

@RequiredArgsConstructor
@Service
public class FeeService {

    private final WeatherDataRepository weatherDataRepository;


    private double calculateRegionalBaseFee(DeliveryFeeRequestDto request) {
        double regionalBaseFee = 0.0;

       Optional<WeatherDataEntity> data = weatherDataRepository.findByStation(request.getStation());

       if(data.isEmpty()){
           throw new WeatherDataNotFoundException("No weather data founf for " + request.getStation());
       }
       WeatherDataEntity weatherInfo = data.get();

       String city = request.getStation();

       City station = fromCityName(city);

        switch (station) {
            case TALLINN:
                if ("CAR".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 4.0;
                } else if ("SCOOTER".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 3.5;
                } else if ("BIKE".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 3.0;
                }
                break;
            case TARTU:
                if ("CAR".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 3.5;
                } else if ("SCOOTER".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 3.0;
                } else if ("BIKE".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 2.5;
                }
                break;
            case PÄRNU:
                if ("CAR".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 3.0;
                } else if ("SCOOTER".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 2.5;
                } else if ("BIKE".equalsIgnoreCase(request.getVehicleType())) {
                    regionalBaseFee = 2.0;
                }
                break;
            default:
                throw new RuntimeException("Unsupported city: " + request.getStation());
        }

        return regionalBaseFee;
    }

    private double calculateTemperatureFee(DeliveryFeeRequestDto request) {

        double airTemperature = weatherDataRepository.findByStation(get);


        if ("SCOOTER".equalsIgnoreCase(request.getVehicleType()) || "BIKE".equalsIgnoreCase(request.getVehicleType())) {
            if (airTemperature < -10) {
                return 1.0;
            } else if (airTemperature >= -10 && airTemperature <= 0) {
                return 0.5;
            }
        }
        return 0.0;
    }

    private double calculateWindSpeedFee(String station, String request.getVehicleType()) {
        WeatherDataEntity weatherData = weatherDataRepository.findByStation(station)
                .orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found for station = " + station));

        double windSpeed = weatherData.getWindspeed();

        if ("BIKE".equalsIgnoreCase(request.getVehicleType())) {
            if (windSpeed > 20) {
                throw new RuntimeException("Usage of selected vehicle type is forbidden due to high wind speed.");
            } else if (windSpeed >= 10 && windSpeed <= 20) {
                return 0.5;
            }
        }
        return 0.0;
    }

    private double calculateWeatherPhenomenonFee(String station, String request.getVehicleType()) {
        WeatherDataEntity weatherData = weatherDataRepository.findByStation(station)
                .orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found for station = " + station));

        String phenomenon = weatherData.getPhenomenon();

        if ("SCOOTER".equalsIgnoreCase(request.getVehicleType()) || "BIKE".equalsIgnoreCase(request.getVehicleType())) {
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

    public double calculateDeliveryFee(DeliveryFeeRequestDto request) {
        double rbf = calculateRegionalBaseFee(request.getStation(), request.getVehicleType());
        double atef =
        double wsef =
        double wpef =

        return rbf + atef + wsef + wpef;
    }

}




