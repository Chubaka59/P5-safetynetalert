package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationNotFoundException;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.service.impl.FireStationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FireStationServiceTest {
    FireStationRepository fireStationRepository = mock(FireStationRepository.class);
    FireStationServiceImpl fireStationService;
    FireStation fireStation;
    CreateFireStationDTO createFireStationDTO = mock(CreateFireStationDTO.class);
    UpdateFireStationDTO updateFireStationDTO;

    @BeforeEach
    public void setupPerTest(){
        fireStation = new FireStation();
        fireStationService = new FireStationServiceImpl(fireStationRepository);
    }

    @Test
    public void getFireStationTest(){
        //GIVEN a list should be returned
        List<FireStation> fireStationList = new ArrayList<>();
        when(fireStationRepository.getAllFireStations()).thenReturn(fireStationList);

        //WHEN the method is called
        fireStationService.getFireStations();

        //THEN fireStationRepository.getAllFireStation is called 1 time
        verify(fireStationRepository, times(1)).getAllFireStations();
    }

    @Test
    public void addTest(){
        //GIVEN the fireStation we would create doesn't exist
        when(fireStationRepository.getFireStation(createFireStationDTO.getAddress())).thenReturn(Optional.empty());

        //WHEN we would add the fireStation
        fireStationService.add(createFireStationDTO);

        //THEN fireStationRepository.add is called 1 time
        verify(fireStationRepository, times(1)).add(any(CreateFireStationDTO.class));
    }

    @Test
    public void addWhenFireStationAlreadyExistTest(){
        //GIVEN the fireStation we would add already exist
        when(fireStationRepository.getFireStation(createFireStationDTO.getAddress())).thenReturn(Optional.of(fireStation));

        //WHEN we would add the fireStation THEN an exception is raised
        assertThrows(FireStationAlreadyExistException.class, () -> fireStationService.add(createFireStationDTO));
    }

    @Test
    public void deleteTest(){
        //GIVEN the fireStation we would delete is found
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.of(fireStation));

        //WHEN we delete the fireStation
        fireStationService.delete(anyString());

        //THEN fireStationRepository.delete is called 1 time
        verify(fireStationRepository, times(1)).delete(any(FireStation.class));
    }

    @Test
    public void deleteWhenFireStationIsNotFoundTest(){
        //GIVEN the fireStation we would delete is not found
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.empty());

        //WHEN we would delete THEN an exception is raised
        assertThrows(FireStationNotFoundException.class, () -> fireStationService.delete(anyString()));
    }

    @Test
    public void updateTest(){
        //GIVEN the fireStation we would update is found
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.of(fireStation));

        //WHEN we update
        fireStationService.update(updateFireStationDTO, anyString());

        //THEN personRepository.update is called 1 time
        verify(fireStationRepository, times(1)).update(fireStation, updateFireStationDTO);
    }

    @Test
    public void updateWhenFireStationIsNotFoundTest(){
        //GIVEN the fireStation we would update is not found
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.empty());

        //WHEN we would update THEN an exception is raised
        assertThrows(FireStationNotFoundException.class, () -> fireStationService.update(updateFireStationDTO, anyString()));
    }
}
