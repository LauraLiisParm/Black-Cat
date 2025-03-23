package com.laura.liis.calculator.repository;

import com.laura.liis.calculator.entity.WeatherDataEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

@DataJpaTest
class WeatherDataRepositoryTest {

    @Autowired
    private WeatherDataRepository repository;

    @Test
    void canSaveWeatherData() {
        repository.save(WeatherDataEntity.builder()
                .station("SOME_STATION")
                .airtemperature(10)
                .wmocode("WMOCODE")
                .timestamp(Instant.now())
                .windspeed(12)
                .phenomenon("PHENOMENON")
                .build());

        List<WeatherDataEntity> saved = repository.findAll();

        Assertions.assertThat(saved.size()).isEqualTo(1);
    }

}