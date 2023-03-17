package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.impl.FireStationRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FireStationRepositoryTest {
    private DataRepository dataRepository = mock(DataRepository.class);
    private FireStationRepository fireStationRepository;
    private FireStation fireStation;
    private CreateFireStationDTO createFireStationDTO;
    private UpdateFireStationDTO updateFireStationDTO;
    private List<FireStation> fireStationList;

    @BeforeEach
    public void setupPerTest(){
        fireStation = new FireStation();
        createFireStationDTO = new CreateFireStationDTO();
        updateFireStationDTO = new UpdateFireStationDTO();
        fireStationRepository = new FireStationRepositoryImpl(dataRepository);
        fireStationList = new ArrayList<>();
    }

    @Test
    public void getAllFireStationsTest(){
        //GIVEN a list should be returned
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN the method is called
        fireStationRepository.getAllFireStations();

        //THEN dataRepository.getFireStation is called 1 time
        verify(dataRepository, times(1)).getFireStations();
    }

    @Test
    public void getFireStationTest(){
        //GIVEN a fireStation is in the list
        fireStation.setAddress("test");
        fireStationList.add(fireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN we try to find this fireStation
        Optional<FireStation> optionalFireStation = fireStationRepository.getFireStation("test");

        //THEN the fireStation is returned as Optional
        verify(dataRepository, times(1)).getFireStations();
        assertEquals(optionalFireStation.get(), fireStation);
    }

    @Test
    public void addTest(){
        //GIVEN there is a fireStation to add
        createFireStationDTO.setAddress("test");
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN we add the fireStation
        fireStation = fireStationRepository.add(createFireStationDTO);

        //THEN the fireStation has been added to the list
        assertEquals(fireStationList.get(0), fireStation);
    }

    @Test
    public void deleteTest(){
        //GIVEN there is a fireStation to delete in the list
        fireStation.setAddress("test");
        fireStationList.add(fireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN we delete the fireStation
        fireStation = fireStationRepository.delete(fireStation);

        //THEN the fireStation isn't in the list anymore
        assertEquals(fireStationList.size(), 0);
    }

    @Test
    public void updateTest(){
        //GIVEN there is a fireStation to update in the list and update information
        fireStation.setAddress("test");
        fireStationList.add(fireStation);

        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        updateFireStationDTO.setStation(1);

        //WHEN the fireStation is updated
        fireStation = fireStationRepository.update(fireStation, updateFireStationDTO);

        //THEN the information of the person are updated
        assertEquals(fireStationList.get(0), fireStation);
    }

    @Test
    public void getAddressFromStationNumber(){
        //GIVEN a fireStation is in the list
        fireStation.setAddress("test1");
        fireStation.setStation(1);
        fireStationList.add(fireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN we search for the addresses of the station
        List<String> address = fireStationRepository.getAddressFromStationNumber(1);

        //THEN it return a list of addresses
        assertEquals(1, fireStationList.size());
        assertEquals(List.of("test1"), address);
    }
}
