package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.dto.fire.FireDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationNotFoundException;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationControllerTest {
    private FireStationService fireStationService = mock(FireStationService.class);
    @MockBean
    private FireStationController fireStationController;

    @BeforeEach
    public void setupPerTest(){
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
        ResponseEntity<FireStation> response = fireStationController.add(new CreateFireStationDTO());

        //THEN the fireStation is created
        then(fireStationService).should().add(any(CreateFireStationDTO.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addWhenFireStationAlreadyExistTest(){
        //GIVEN the fireStation to add already exist
        given(fireStationService.add(any(CreateFireStationDTO.class))).willThrow(new FireStationAlreadyExistException(anyString()));

        //WHEN we add the fireStation
        ResponseEntity<FireStation> response = fireStationController.add(new CreateFireStationDTO());

        //THEN the response status is conflict
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteTest(){
        //GIVEN a fireStation needs to be deleted
        FireStation existingFireStation = new FireStation();
        given(fireStationService.delete(anyString())).willReturn(existingFireStation);

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
        FireStation existingFireStation = new FireStation();
        given(fireStationService.update(new UpdateFireStationDTO(), "test")).willReturn(existingFireStation);

        //WHEN we update the fireStation
        ResponseEntity<FireStation> response = fireStationController.update(new UpdateFireStationDTO(), "test");

        //THEN the method to update is called and the response is OK
        then(fireStationService).should().update(new UpdateFireStationDTO(), "test");
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

    @Test
    public void getPhoneAlertTest(){
        //GIVEN this will return a list
        when(fireStationService.getPhoneAlert(anyInt())).thenReturn(anyList());

        //WHEN we call the method
        fireStationController.getPhoneAlert(1);

        //THEN fireStationService.getPhoneAlert is called once
        verify(fireStationService, times(1)).getPhoneAlert(anyInt());
    }

    @Test
    public void getFire(){
        //GIVEN this will return a FireDTO
        when(fireStationService.getFire(anyString())).thenReturn(any(FireDTO.class));

        //WHEN the method is called
        fireStationController.getFire("test");

        //THEN fireStationService.getFire is called once
        verify(fireStationService, times(1)).getFire(anyString());
    }

    @Test
    public void getFlood(){
        //GIVEN this should use a list as parameter and return a list
        List<Integer> stations = new ArrayList<>();
        when(fireStationService.getFlood(anyList())).thenReturn(anyList());

        //WHEN the method is called
        fireStationService.getFlood(stations);

        //THEN fireStationService.getFlood is called once
        verify(fireStationService, times(1)).getFlood(anyList());
    }
}
