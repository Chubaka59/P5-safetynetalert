package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.dto.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationNotFoundException;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationControllerTest {
    private FireStationService fireStationService = mock(FireStationService.class);
    private FireStationController fireStationController;
    private FireStation fireStation;
    private CreateFireStationDTO createFireStationDTO;
    private UpdateFireStationDTO updateFireStationDTO;

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
        given(fireStationService.add(any(CreateFireStationDTO.class))).willReturn(any(FireStation.class));

        //WHEN we post a new fireStation
        ResponseEntity<FireStation> response = fireStationController.add(createFireStationDTO);

        //THEN the fireStation is created
        then(fireStationService).should().add(createFireStationDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addWhenFireStationAlreadyExistTest(){
        //GIVEN the fireStation to add already exist
        given(fireStationService.add(createFireStationDTO)).willThrow(new FireStationAlreadyExistException(anyString()));

        //WHEN we add the fireStation
        ResponseEntity<FireStation> response = fireStationController.add(createFireStationDTO);

        //THEN the response status is conflict
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteTest(){
        //GIVEN a fireStation needs to be deleted
        given(fireStationService.delete(anyString())).willReturn(fireStation);

        //WHEN we request to delete the fireStation
        ResponseEntity<FireStation> response = fireStationController.delete(anyString());

        //THEN the method to delete is called and the response is OK
        then(fireStationService).should().delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteNotExistingFireStationTest(){
        //GIVEN a fireStation that doesn't exist will be deleted
        given(fireStationService.delete(anyString())).willThrow(new FireStationNotFoundException(anyString()));

        //WHEN we request to delete the fireStation
        ResponseEntity<FireStation> response = fireStationController.delete(anyString());

        //THEN the method to delete is called and the response is NOT_FOUND
        then(fireStationService).shouldHaveNoInteractions();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTest(){
        //GIVEN a fireStation will be updated
        given(fireStationService.update(updateFireStationDTO, "test")).willReturn(fireStation);

        //WHEN we update the fireStation
        ResponseEntity<FireStation> response = fireStationController.update(updateFireStationDTO, "test");

        //THEN the method to update is called and the response is OK
        then(fireStationService).should().update(updateFireStationDTO, "test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateNotExistingFireStationTest(){
        //GIVEn the fireStation we would update doesn't exist
        given(fireStationService.update(any(UpdateFireStationDTO.class), anyString())).willThrow(new FireStationNotFoundException(anyString()));

        //WHEN we update the fireStation
        ResponseEntity<FireStation> response = fireStationController.update(any(UpdateFireStationDTO.class), anyString());

        //THEN the response is NOT_FOUND
        then(fireStationService).shouldHaveNoInteractions();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getPersonsFromFireStationTest(){
        //GIVEN this will return a fireStationDTO
        when(fireStationService.getPersonsFromFireStation(anyInt())).thenReturn(any(FireStationDTO.class));

        //WHEN the method is called
        fireStationController.getPersonsFromFireStation(1);

        //THEN fireStationService.getPersonsFromFireStation is called once
        verify(fireStationService, times(1)).getPersonsFromFireStation(anyInt());
    }
}
