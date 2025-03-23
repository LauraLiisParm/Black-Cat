package com.laura.liis.calculator.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum City {
    TALLINN("Tallinn-Harku"),
    TARTU("Tartu-Tõravere"),
    PÄRNU("Pärnu"),
    ;
    private final String cityName;
    City(String cityName) {
        this.cityName = cityName;
    }
    public static City fromCityName(String cityname) {
        return Arrays.stream(City.values())
                .filter(city -> city.getCityName().equals(cityname))
                .findFirst()
                .orElseThrow();
    }
}
