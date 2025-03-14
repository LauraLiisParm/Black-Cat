package com.laura.liis.calculator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weather_data")
public class WeatherDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
