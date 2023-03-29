package com.openclassrooms.safetynetalert.integration;

import com.openclassrooms.safetynetalert.repository.impl.DataRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    DataRepositoryImpl dataRepository;

    @BeforeEach
    public void init() throws Exception {
        dataRepository.getDataFromFile();
    }

    @Test
    public void getTest() throws Exception {
        //WHEN we perform a get THEN the status is OK
        mockMvc.perform(get("/firestation"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addTest() throws Exception {
        //GIVEN we need to add a fireStation
        MockHttpServletRequestBuilder requestBuilders = post("/firestation")
                .content("""
                            {
                                "address":"1 Test St",
                                "station":"3"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN we post a new fireStation THEN the status is CREATED
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addWhenAFieldIsMissingTest() throws Exception {
        //GIVEN we need to add a fireStation where a field is missing
        MockHttpServletRequestBuilder requestBuilders = post("/firestation")
                .content("""
                            {
                                "station":"3"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN we post the fireStation THEN the status is BAD_REQUEST
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWhenFireStationAlreadyExistTest() throws Exception{
        //GIVEN we need to add a fireStation that already exist
        MockHttpServletRequestBuilder requestBuilders = post("/firestation")
                .content("""
                            {
                                "address":"1509 Culver St",
                                "station":"3"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN we add the fireStation THEN the status is CONFLICT
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteTest() throws Exception{
        //WHEN we delete a fireStation THEN the status is OK
        mockMvc.perform(delete("/firestation/1509 Culver St"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingFireStationTest() throws Exception {
        //WHEN we delete a fireStation that doesn't exist THEN the status is NOT_FOUND
        mockMvc.perform(delete("/firestation/test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTest() throws Exception {
        //GIVEN we need to update a fireStation
        MockHttpServletRequestBuilder requestBuilders = put("/firestation/1509 Culver St")
                .content("""
                            {
                                "station":"2"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN the update request is sent THEN the status is OK
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateNotExistingFireStationTest() throws Exception{
        //GIVEN we will update a fireStation that doesn't exist
        MockHttpServletRequestBuilder requestBuilders = put("/firestation/test")
                .content("""
                            {
                                "station":"3"
                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN the update request is sent THEN the status is NOT_FOUND
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWhenAFieldIsMissingTest() throws Exception{
        //GIVEN we will try to update a fireStation where a field is missing
        MockHttpServletRequestBuilder requestBuilders = put("/firestation/1509 Culver St")
                .content("""
                            {

                            }\
                        """)
                .contentType(MediaType.APPLICATION_JSON);

        //WHEN the update request is sent THEN the status is BAD_REQUEST
        mockMvc.perform(requestBuilders)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPersonsFromFireStationTest() throws Exception {
        //WHEN the request is sent THEN we get a list of person with the count of major and minor
        mockMvc.perform(get("/firestations?stationNumber=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getPhoneAlertListTest() throws Exception {
        //WHEN the request is sent THEN we get a list of phone number
        mockMvc.perform(get("/phoneAlert?firestation=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationFromAddressTest() throws Exception {
        //WHEN the request is sent THEN we get a person with the correct information
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getHomeListFromFireStationListTest() throws Exception {
        //WHEN the request is sent THEN we get the list with an address and a firstName of both stations
        mockMvc.perform(get("/flood/stations?stations=1,2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
