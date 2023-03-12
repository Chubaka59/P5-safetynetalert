package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.impl.FireStationRepositoryImpl;
import com.openclassrooms.safetynetalert.service.impl.FireStationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class FireStationServiceTest {
    FireStationRepositoryImpl fireStationRepository = mock(FireStationRepositoryImpl.class);
    FireStationServiceImpl fireStationService;
    FireStation fireStation;

    @BeforeEach
    public void setupPerTest(){
        fireStation = new FireStation();
        fireStationService = new FireStationServiceImpl(fireStationRepository);
    }

    @Test
    public void getFireStationTest(){
        //GIVEN a list should be returned
        List<FireStation> fireStationList = new ArrayList<>();
        when(fireStationRepository.getAllFireStation()).thenReturn(fireStationList);

        //WHEN the method is called
        fireStationService.getFireStations();

        //THEN fireStationRepository.getAllFireStation is called 1 time
        verify(fireStationRepository, times(1)).getAllFireStation();
    }

    @Test
    public void addTest(){
        //GIVEN when the method is called, it should return a boolean
        when(fireStationRepository.add(any(FireStation.class))).thenReturn(true);

        //WHEN the method is called
        fireStationService.add(fireStation);

        //THEN fireStationRepository.add is called 1 time
        verify(fireStationRepository, times(1)).add(any(FireStation.class));
    }

    @Test
    public void deleteTest(){
        //GIVEN when the method is called, it should return a boolean
        when(fireStationRepository.delete(anyString())).thenReturn(true);

        //WHEN the method is called
        fireStationService.delete(anyString());

        //THEN fireStationRepository.delete is called 1 time
        verify(fireStationRepository, times(1)).delete(anyString());
    }

    @Test
    public void updateTest(){
        //GIVEN when the method is called, it should return a boolean
        when(fireStationRepository.update(any(FireStation.class), anyString())).thenReturn(true);

        //WHEN the method is called
        fireStationService.update(fireStation, "test");

        //THEN personRepository.update is called 1 time
        verify(fireStationRepository, times(1)).update(any(FireStation.class), anyString());
    }
}
