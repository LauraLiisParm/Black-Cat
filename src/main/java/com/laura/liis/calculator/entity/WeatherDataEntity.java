package com.laura.liis.calculator.entity;

import com.laura.liis.calculator.enums.City;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "weather_data")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "station", nullable = false)
    private String station;

    @NotNull
    @Column(name = "wmocode", nullable = false)
    private String wmocode;

    @NotNull
    @Column(name = "airtemperature", nullable = false)
    private double airtemperature;

    @NotNull
    @Column(name = "windspeed", nullable = false)
    private double windspeed;

    @NotNull
    @Column(name = "phenomenon", nullable = false)
    private String phenomenon;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

}
