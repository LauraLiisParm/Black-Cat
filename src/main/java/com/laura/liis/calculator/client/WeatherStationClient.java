package com.laura.liis.calculator.client;

import com.laura.liis.calculator.dto.ObservationsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherStationClient {

    private static final String URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private final RestTemplate restTemplate;

    public ObservationsDto getWeatherData() {
        log.warn("GETTING DATA");
        final var request = RequestEntity
                .get(URL)
                .accept(MediaType.APPLICATION_XML)
                .build();

        ResponseEntity<ObservationsDto> response = restTemplate
                .exchange(request, ObservationsDto.class);
        log.warn(response.getBody().toString());

        return null;
    }
}
