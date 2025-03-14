package com.laura.liis.calculator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "station")
public class StationDto {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("name")
    private String name;

    @JsonProperty("wmocode")
    private String wmocode;


    @JsonProperty("airtemperature")
    private double airtemperature;


    @JsonProperty("windspeed")
    private String windspeed;


    @JsonProperty("phenomenon")
    private String phenomenon;

}
