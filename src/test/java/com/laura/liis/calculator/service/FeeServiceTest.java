package com.laura.liis.calculator.service;

import com.laura.liis.calculator.entity.WeatherDataEntity;
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
}