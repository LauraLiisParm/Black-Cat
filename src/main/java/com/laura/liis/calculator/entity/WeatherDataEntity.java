package com.laura.liis.calculator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "weather_data")
public class WeatherDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_data_id_gen")
    @SequenceGenerator(name = "weather_data_id_gen", sequenceName = "weather_data_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "station", nullable = false)
    private String station;

    @NotNull
    @Column(name = "wmocode", nullable = false)
    private String wmocode;

    @NotNull
    @Column(name= "airtemeperature", nullable = false)
    private double airtemperature;

    @NotNull
    @Column(name = "windspeed", nullable = false)
    private String windspeed;

    @NotNull
    @Column(name="phenomenon", nullable = false)
    private String phenomenon;
}
