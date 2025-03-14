package com.laura.liis.calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JacksonXmlRootElement(localName = "observations")
public class ObservationsDto {

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "timestamp")
    private String timestamp;



    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("station")
    private List<StationDto> station;

}
