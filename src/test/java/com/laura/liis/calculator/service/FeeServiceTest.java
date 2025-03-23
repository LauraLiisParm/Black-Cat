package com.laura.liis.calculator.service;

import com.laura.liis.calculator.entity.WeatherDataEntity;
import com.laura.liis.calculator.exception.UnsupportedCityException;
import com.laura.liis.calculator.exception.UnsupportedVehicleTypeException;
import com.laura.liis.calculator.exception.UsageOfSelectedVehicleIsForbiddenException;
import com.laura.liis.calculator.exception.WeatherDataNotFoundException;
import com.laura.liis.calculator.repository.WeatherDataRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FeeServiceTest {

    private static final String STATION_TARTU = "TARTU";
    private static final String VEHICLE_TYPE_BIKE = "BIKE";

    private static final String VEHICLE_TYPE_CAR = "CAR";
    private static final String STATION_TALLINN = "TALLINN";

    private static final String VEHICLE_TYPE_SCOOTER = "SCOOTER";
    private static final String STATION_PÄRNU = "PÄRNU";

    private static final String VEHICLE_TYPE_bbdjbkjw = "bbdjbkjw";
    private static final String STATION_jsojwjs = "jsojwjs";

    @Mock
    private WeatherDataRepository repository;

    private FeeService feeService;

    @BeforeEach
    void setup() {
        feeService = new FeeService(
                repository
        );
    }

    @Test
    void givenStationTartuAndVehicleBikeShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_TARTU))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("TARTU")
                        .airtemperature(-2.1)
                        .windspeed(4.7)
                        .phenomenon("Light snow shower")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_TARTU, VEHICLE_TYPE_BIKE);

        Assertions.assertThat(fee).isEqualTo(4);
    }

    @Test
    void givenStationTartuAndVehicleScooterShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_TARTU))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("TARTU")
                        .airtemperature(-19)
                        .windspeed(6.0)
                        .phenomenon("Variable clouds")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_TARTU, VEHICLE_TYPE_SCOOTER);

        Assertions.assertThat(fee).isEqualTo(4);
    }

    @Test
    void givenStationTartuAndVehicleCarShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_TARTU))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("TARTU")
                        .airtemperature(15)
                        .windspeed(3.0)
                        .phenomenon("Clear")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_TARTU, VEHICLE_TYPE_CAR);

        Assertions.assertThat(fee).isEqualTo(3.5);
    }

    @Test
    void givenStationTallinnAndVehicleCarShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_TALLINN))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("TALLINN")
                        .airtemperature(0.0)
                        .windspeed(5.0)
                        .phenomenon("Clear sky")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_TALLINN, VEHICLE_TYPE_CAR);

        Assertions.assertThat(fee).isEqualTo(4);
    }

    @Test
    void givenStationTallinnAndVehicleScooterShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_TALLINN))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("TALLINN")
                        .airtemperature(3)
                        .windspeed(5.0)
                        .phenomenon("Heavy shower")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_TALLINN, VEHICLE_TYPE_SCOOTER);

        Assertions.assertThat(fee).isEqualTo(4);
    }

    @Test
    void givenStationTallinnAndVehicleBikeShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_TALLINN))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("TALLINN")
                        .airtemperature(5)
                        .windspeed(1.0)
                        .phenomenon("Fog")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_TALLINN, VEHICLE_TYPE_BIKE);

        Assertions.assertThat(fee).isEqualTo(3);
    }

    @Test
    void givenStationParnuAndVehicleScooterShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_PÄRNU))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("PÄRNU")
                        .airtemperature(-5.0)
                        .windspeed(6.0)
                        .phenomenon("Heavy snow")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_PÄRNU, VEHICLE_TYPE_SCOOTER);

        Assertions.assertThat(fee).isEqualTo(4);
    }

    @Test
    void givenStationParnuAndVehicleBikeShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_PÄRNU))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("PÄRNU")
                        .airtemperature(-29)
                        .windspeed(3.0)
                        .phenomenon("Heavy snow")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_PÄRNU, VEHICLE_TYPE_BIKE);

        Assertions.assertThat(fee).isEqualTo(4);
    }

    @Test
    void givenStationParnuAndVehicleCarShouldCalculateCorrectDeliveryFee() {
        Mockito.when(repository.findByStation(STATION_PÄRNU))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("PÄRNU")
                        .airtemperature(25)
                        .windspeed(3.0)
                        .phenomenon("Clear")
                        .build()));

        Double fee = feeService.calculateDeliveryFee(STATION_PÄRNU, VEHICLE_TYPE_CAR);

        Assertions.assertThat(fee).isEqualTo(3);
    }

    @Test
    void givenStationTallinnAndVehicleBikeWithHighWindSpeedShouldThrowForbiddenException() {
        Mockito.when(repository.findByStation(STATION_TALLINN))
                .thenReturn(Optional.of(WeatherDataEntity.builder()
                        .station("TALLINN")
                        .airtemperature(2.0)
                        .windspeed(25.0)
                        .phenomenon("Clear sky")
                        .build()));

        Assertions.assertThatThrownBy(() -> feeService.calculateDeliveryFee(STATION_TALLINN, VEHICLE_TYPE_BIKE))
                .isInstanceOf(UsageOfSelectedVehicleIsForbiddenException.class);
    }

    @Test
    void givenStationFaultyAndVehicleFaultyShouldThrowUnsupportedCityException() {
        Assertions.assertThatThrownBy(() -> feeService.calculateDeliveryFee(STATION_jsojwjs, VEHICLE_TYPE_bbdjbkjw))
                .isInstanceOf(UnsupportedCityException.class);
    }

    @Test
    void givenStationTartuAndVehicleTypeFaultyShouldThrowUnsupportedVehicleTypeException() {
        Assertions.assertThatThrownBy(() -> feeService.calculateDeliveryFee(STATION_TARTU, VEHICLE_TYPE_bbdjbkjw))
                .isInstanceOf(UnsupportedVehicleTypeException.class);
    }

    @Test
    void givenStationFaultyAndVehicleTypeCarShouldThrowUnsupportedCityException() {
        Assertions.assertThatThrownBy(() -> feeService.calculateDeliveryFee(STATION_jsojwjs, VEHICLE_TYPE_CAR))
                .isInstanceOf(UnsupportedCityException.class);
    }

    @Test
    void givenStationTallinnAndVehicleBikeShouldThrowWeatherDataNotFoundException() {
        Mockito.when(repository.findByStation(STATION_TALLINN))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> feeService.calculateDeliveryFee(STATION_TALLINN, VEHICLE_TYPE_BIKE))
                .isInstanceOf(WeatherDataNotFoundException.class);
    }
}