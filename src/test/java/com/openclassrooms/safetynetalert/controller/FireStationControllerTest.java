package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.service.impl.FireStationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {
    @Autowired
    MockMvc mockMvc;
    FireStationServiceImpl fireStationService = mock(FireStationServiceImpl.class);
    FireStationController fireStationController;
    FireStation fireStation;

    @BeforeEach
    public void setupPerTest(){
        fireStation = new FireStation();
        fireStationController = new FireStationController(fireStationService);
    }

    @Test
    public void findAllFireStationsTest(){
        //WHEN we request to find all firstStations is sent
        fireStationController.findAllFireStations();

        //THEN personService.getFireStations is called
        verify(fireStationService, times(1)).getFireStations();
    }

    @Test
    public void addTest(){
        //GIVEN we need to add a person
        given(fireStationService.add(any(FireStation.class))).willReturn(true);

        //WHEN we post a new fireStation
        ResponseEntity<FireStation> response = fireStationController.add(fireStation);

        //THEN the fireStation is created
        then(fireStationService).should().add(fireStation);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addWhenAFieldIsMissingTest() throws Exception {
        //TODO this is an IT, need to move it in IT Test
        String body = "{ \"station\":\"3\" },";
        when(fireStationService.add(any(FireStation.class))).thenReturn(true);

        //WHEN we post a new person
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/firestations")
                .accept(MediaType.APPLICATION_JSON).content(body)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        //THEN the person is created on the correct address
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void addWhenFireStationAlreadyExistTest(){
        //GIVEN the fireStation to add already exist
        given(fireStationService.add(any(FireStation.class))).willReturn(false);

        //WHEN we add the fireStation
        ResponseEntity<FireStation> response = fireStationController.add(fireStation);

        //THEN the response status is conflict
        then(fireStationService).should().add(fireStation);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteTest(){
        //GIVEN a fireStation needs to be deleted
        given(fireStationService.delete(anyString())).willReturn(true);

        //WHEN we request to delete the fireStation
        ResponseEntity<FireStation> response = fireStationController.delete(anyString());

        //THEN the method to delete is called and the response is OK
        then(fireStationService).should().delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteNotExistingFireStationTest(){
        //GIVEN a fireStation that doesn't exist will be deleted
        given(fireStationService.delete(anyString())).willReturn(false);

        //WHEN we request to delete the fireStation
        ResponseEntity<FireStation> response = fireStationController.delete(anyString());

        //THEN the method to delete is called and the response is NOT_FOUND
        then(fireStationService).should().delete(anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTest(){
        //GIVEN a fireStation will be updated
        given(fireStationService.update(any(FireStation.class), anyString())).willReturn(true);

        //WHEN we update the fireStation
        ResponseEntity<FireStation> response = fireStationController.update(fireStation, "test");

        //THEN the method to update is called and the response is OK
        then(fireStationService).should().update(any(FireStation.class), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateNotExistingFireStationTest(){
        //GIVEn the fireStation we would update doesn't exist
        given(fireStationService.update(any(FireStation.class), anyString())).willReturn(false);

        //WHEN we update the fireStation
        ResponseEntity<FireStation> response = fireStationController.update(fireStation, "test");

        //THEN the response is NOT_FOUND
        then(fireStationService).should().update(any(FireStation.class), anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
