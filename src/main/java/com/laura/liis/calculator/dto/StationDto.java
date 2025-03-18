package com.laura.liis.calculator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "station")
public class StationDto {

    @JacksonXmlProperty(localName = "name")  // Property mapping for "name"
    private String name;

    @JacksonXmlProperty(localName = "wmocode")  // Property mapping for "wmocode"
    private String wmocode;

    @JacksonXmlProperty(localName = "airtemperature")  // Property mapping for "airtemperature"
    private double airtemperature;

    @JacksonXmlProperty(localName = "windspeed")  // Property mapping for "windspeed"
    private double windspeed;

    @JacksonXmlProperty(localName = "phenomenon")  // Property mapping for "phenomenon"
    private String phenomenon;


}
