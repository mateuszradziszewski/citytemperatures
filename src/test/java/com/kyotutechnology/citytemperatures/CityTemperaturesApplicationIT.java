package com.kyotutechnology.citytemperatures;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class CityTemperaturesApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void respondsWithAverageTemperaturesForExistingCity() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/average-temperatures/Tokyo"))
            .andExpect(request().asyncStarted())
            .andReturn();
        this.mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"year\":2023,\"averageTemperature\":16.0},{\"year\":2024,\"averageTemperature\":25.0}]"));
    }

    @Test
    void respondsWithEmptyArrayForNonExistentCity() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/average-temperatures/MissingCity"))
            .andExpect(request().asyncStarted())
            .andReturn();
        this.mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }
}
