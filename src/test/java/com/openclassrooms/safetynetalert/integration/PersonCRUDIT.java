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
    private MockMvc mockMvc;

    @BeforeEach
    public void setupPerTest(){
    }

    @Test
    public void getTest() throws Exception {
        //WHEN we perform a get THEN the status is OK and we can find "John" in the personList
        mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John")));
    }

    @Test
    public void addTest() throws Exception {
        //GIVEN we need to add a person
        MockHttpServletRequestBuilder requestBuilders = post("/person")
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

        //WHEN we post a new person THEN the status is CREATED
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addWhenAFieldIsMissingTest() throws Exception {
        //GIVEN we need to add a person where a field is missing
        MockHttpServletRequestBuilder requestBuilders = post("/person")
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

        //WHEN we add the person THEN the status is BAD_REQUEST
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWhenPersonAlreadyExistTest() throws Exception {
        //GIVEN we need to add a person that already exist
        MockHttpServletRequestBuilder requestBuilders = post("/person")
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

        //WHEN we add the person THEN the status is CONFLICT
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteTest() throws Exception {
        //WHEN we delete a person THEN the status is OK
        mockMvc.perform(delete("/person/John&Boyd"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingPersonTest() throws Exception {
        //WHEN we delete a person that doesn't exist THEN the status is NOT_FOUND
        mockMvc.perform(delete("/person/test&test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void UpdateTest() throws Exception {
        //GIVEN we need to update a person
        MockHttpServletRequestBuilder requestBuilders = put("/person/John&Boyd")
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

        //WHEN the update request is sent THEN the status is OK
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateNotExistingPersonTest() throws Exception {
        //GIVEN we will update a person that doesn't exist
        MockHttpServletRequestBuilder requestBuilders = put("/person/test&test")
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

        //WHEN the update request is sent THEN the status is NOT_FOUND
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWhenAFieldIsMissingTest() throws Exception {
        //GIVEN we will try to update a person where a field is missing
        MockHttpServletRequestBuilder requestBuilders = put("/person/John&Boyd")
                .content("""
                            {
                                "city": "test",
                                "zip": "test",
                                "phone": "0123456789",
                                "email": "test@test"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN the update request is sent THEN the status is BAD_REQUEST
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
