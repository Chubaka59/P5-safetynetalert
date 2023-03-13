package com.openclassrooms.safetynetalert.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonCRUDIT {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setupPerTest(){
    }

    @Test
    public void testGet() throws Exception {
        //WHEN we perform a get THEN the status is OK and we can find "John" in the personList
        mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John")));
    }

    @Test
    public void testAdd() throws Exception {
        MockHttpServletRequestBuilder requestBuilders = post("/persons")
                .content("""
                            {
                                "firstName": "Joffrey",
                                "lastName": "Lefebvre",
                                "address": "test",
                                "city": "test",
                                "zip": "test",
                                "phone": "0123456789",
                                "email": "test@test"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddWhenAFieldIsMissing() throws Exception {
        MockHttpServletRequestBuilder requestBuilders = post("/persons")
                .content("""
                            {
                                "firstName": "Joffrey",
                                "lastName": "Lefebvre",
                                "address": "test",
                                "city": "test",
                                "phone": "0123456789",
                                "email": "test@test"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddWhenPersonAlreadyExist() throws Exception {
        MockHttpServletRequestBuilder requestBuilders = post("/persons")
                .content("""
                            {
                                "firstName": "John",
                                "lastName": "Boyd",
                                "address": "1509 Culver St",
                                "city": "Culver",
                                "zip": "97451",
                                "phone": "841-874-6512",
                                "email": "jaboyd@email.com"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/persons/John&Boyd"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNotExistingPerson() throws Exception {
        mockMvc.perform(delete("/persons/test&test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdate() throws Exception {
        MockHttpServletRequestBuilder requestBuilders = put("/persons/John&Boyd")
                .content("""
                            {
                                "address": "test",
                                "city": "test",
                                "zip": "test",
                                "phone": "0123456789",
                                "email": "test@test"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateNotExistingPerson() throws Exception {
        MockHttpServletRequestBuilder requestBuilders = put("/persons/test&test")
                .content("""
                            {
                                "address": "test",
                                "city": "test",
                                "zip": "test",
                                "phone": "0123456789",
                                "email": "test@test"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWhenAFieldIsMissing() throws Exception {
        MockHttpServletRequestBuilder requestBuilders = put("/persons/John&Boyd")
                .content("""
                            {
                                "city": "test",
                                "zip": "test",
                                "phone": "0123456789",
                                "email": "test@test"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
