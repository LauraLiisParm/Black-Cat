package com.laura.liis.calculator.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FeeCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void calculatesCorrectFeeForTartuAndBike() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/calculate")
                                .param("cityName", "TARTU")
                                .param("vehicleType", "BIKE")
                )
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}