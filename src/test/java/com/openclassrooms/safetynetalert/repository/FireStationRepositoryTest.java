package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.impl.FireStationRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FireStationRepositoryTest {
    private DataRepository dataRepository = mock(DataRepository.class);
    @MockBean
    private FireStationRepository fireStationRepository;

    @BeforeEach
    public void setupPerTest(){
        fireStationRepository = new FireStationRepositoryImpl(dataRepository);
    }

    @Test
    public void getAllFireStationsTest(){
        //GIVEN a list should be returned
        FireStation existingFireStation = new FireStation();
        when(dataRepository.getFireStations()).thenReturn(List.of(existingFireStation));

        //WHEN the method is called
        fireStationRepository.getAllFireStations();

        //THEN dataRepository.getFireStation is called 1 time
        verify(dataRepository, times(1)).getFireStations();
    }

    @Test
    public void getFireStationTest(){
        //GIVEN a fireStation is in the list
        FireStation existingFireStation = new FireStation("test", 1);
        when(dataRepository.getFireStations()).thenReturn(List.of(existingFireStation));

        //WHEN we try to find this fireStation
        Optional<FireStation> optionalFireStation = fireStationRepository.getFireStation("test");

        //THEN the fireStation is returned as Optional
        verify(dataRepository, times(1)).getFireStations();
        assertEquals(Optional.of(existingFireStation), optionalFireStation);
    }

    @Test
    public void addTest(){
        //GIVEN there is a fireStation to add
        List<FireStation> fireStationList = new ArrayList<>();
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN we add the fireStation
        FireStation addedFireStation = fireStationRepository.add(new CreateFireStationDTO("test", 1));

        //THEN the fireStation has been added to the list
        assertEquals(fireStationList.get(0), addedFireStation);
    }

    @Test
    public void deleteTest(){
        //GIVEN there is a fireStation to delete in the list
        FireStation existingFireStation = new FireStation("test", 1);
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(existingFireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN we delete the fireStation
        FireStation deletedFireStation = fireStationRepository.delete(existingFireStation);

        //THEN the fireStation isn't in the list anymore
        assertEquals(fireStationList.size(), 0);
        assertEquals(existingFireStation, deletedFireStation);
    }

    @Test
    public void updateTest(){
        //GIVEN there is a fireStation to update in the list and update information
        FireStation existingFireStation = new FireStation("test", 1);
        UpdateFireStationDTO updateFireStationDTO = new UpdateFireStationDTO(2);
        when(dataRepository.getFireStations()).thenReturn(List.of(existingFireStation));

        FireStation expectedFireStation = new FireStation("test", 2);

        //WHEN the fireStation is updated
        FireStation updatedFireStation = fireStationRepository.update(existingFireStation, updateFireStationDTO);

        //THEN the information of the person are updated
        assertEquals(expectedFireStation, updatedFireStation);
    }

    @Test
    public void getAddressFromStationNumber(){
        //GIVEN a fireStation is in the list
        FireStation existingFireStation = new FireStation("test", 1);
        when(dataRepository.getFireStations()).thenReturn(List.of(existingFireStation));

        //WHEN we search for the addresses of the station
        List<String> address = fireStationRepository.getAddressFromStationNumber(1);

        //THEN it return a list of addresses
        assertEquals(List.of(existingFireStation.getAddress()), address);
    }
}
