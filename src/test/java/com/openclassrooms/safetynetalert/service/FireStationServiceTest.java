package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.fire.FireDTO;
import com.openclassrooms.safetynetalert.dto.fire.FirePersonDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.PersonDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.flood.FloodDTO;
import com.openclassrooms.safetynetalert.dto.flood.FloodPersonDTO;
import com.openclassrooms.safetynetalert.dto.phonealert.PhoneAlertDTO;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationNotFoundException;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.service.impl.FireStationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FireStationServiceTest {
    private FireStationRepository fireStationRepository = mock(FireStationRepository.class);
    private MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
    private PersonRepository personRepository = mock(PersonRepository.class);
    @MockBean
    private FireStationServiceImpl fireStationService;

    @BeforeEach
    public void setupPerTest(){
        fireStationService = new FireStationServiceImpl(fireStationRepository, personRepository, medicalRecordRepository);
    }

    @Test
    public void getFireStationsTest(){
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
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.empty());

        //WHEN we would add the fireStation
        fireStationService.add(new CreateFireStationDTO());

        //THEN fireStationRepository.add is called 1 time
        verify(fireStationRepository, times(1)).add(any(CreateFireStationDTO.class));
    }

    @Test
    public void addWhenFireStationAlreadyExistTest(){
        //GIVEN the fireStation we would add already exist
        CreateFireStationDTO addExistingFireStation = new CreateFireStationDTO("test", 1);
        FireStation existingFireStation = new FireStation("test", 1);

        when(fireStationRepository.getFireStation(addExistingFireStation.getAddress())).thenReturn(Optional.of(existingFireStation));

        //WHEN we would add the fireStation THEN an exception is raised
        assertThrows(FireStationAlreadyExistException.class, () -> fireStationService.add(addExistingFireStation));
    }

    @Test
    public void deleteTest(){
        //GIVEN the fireStation we would delete is found
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.of(new FireStation()));

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
        FireStation existingFireStation = new FireStation("test", 1);
        UpdateFireStationDTO updatedFireStation = new UpdateFireStationDTO();
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.of(existingFireStation));

        //WHEN we update
        fireStationService.update(updatedFireStation, anyString());

        //THEN personRepository.update is called 1 time
        verify(fireStationRepository, times(1)).update(existingFireStation, updatedFireStation);
    }

    @Test
    public void updateWhenFireStationIsNotFoundTest(){
        //GIVEN the fireStation we would update is not found
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.empty());
        UpdateFireStationDTO updatedFireStation = new UpdateFireStationDTO();

        //WHEN we would update THEN an exception is raised
        assertThrows(FireStationNotFoundException.class, () -> fireStationService.update(updatedFireStation, anyString()));
    }

    @Test
    public void getPersonsFromFireStationTest(){
        //GIVEN there is a minor person in the list
        Person existingPerson = new Person("test", "test", null, null, null, null, null);
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        PersonDTO expectedPerson = new PersonDTO(existingPerson);

        when(fireStationRepository.getAddressFromStationNumber(anyInt())).thenReturn(List.of("test"));
        when(personRepository.findByAddress("test")).thenReturn(List.of(existingPerson));
        when(medicalRecordRepository.getMedicalRecord("test", "test")).thenReturn(Optional.of(existingMedicalRecord));

        //WHEN we create the DTO
        FireStationDTO fireStationDTO = fireStationService.getPersonsFromFireStation(1);

        //THEN those methods are called and the minor is in it
        verify(fireStationRepository, times(1)).getAddressFromStationNumber(anyInt());
        verify(personRepository, times(1)).findByAddress(anyString());
        verify(medicalRecordRepository, times(1)).getMedicalRecord(anyString(), anyString());

        assertEquals(1, fireStationDTO.getNumberOfMinor());
        assertEquals(new FireStationDTO(List.of(expectedPerson), 1L), fireStationDTO);
    }

    @Test
    public void getPhoneAlertTest(){
        //GIVEN it should return an addressList and a personList
        Person existingPerson = new Person("test", "test", null, null, null, "0123456789", null);
        when(fireStationRepository.getAddressFromStationNumber(anyInt())).thenReturn(List.of("test"));
        when(personRepository.findByAddress(anyString())).thenReturn(List.of(existingPerson));

        //WHEN we request for the phoneAlert
        List<PhoneAlertDTO> phoneAlertDTOList = fireStationService.getPhoneAlert(1);

        //THEN a list is returned with person's phone number
        assertEquals(1, phoneAlertDTOList.size());
        assertEquals(List.of(new PhoneAlertDTO(existingPerson)), phoneAlertDTOList);
    }

    @Test
    public void getFireTest(){
        //GIVEN a person is in the list and a fireStation is in the list
        Person existingPerson = new Person("test", "test", "test", null, null, null, null);
        MedicalRecord existingMedicalRecord = new MedicalRecord(null, null, LocalDate.now(), null, null);
        FireStation existingFireStation = new FireStation(null, 1);

        when(personRepository.getAllPersons()).thenReturn(List.of(existingPerson));
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(existingMedicalRecord));
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.of(existingFireStation));

        FirePersonDTO expectedFirePersonDTOList = new FirePersonDTO(existingPerson, existingMedicalRecord);

        //WHEN we get the Fire information
        FireDTO fireDTO = fireStationService.getFire("test");

        //THEN we get the fireStation number and the person information
        assertEquals(new FireDTO(String.valueOf(existingFireStation.getStation()), List.of(expectedFirePersonDTOList)), fireDTO);
    }

    @Test
    public void getFireWhenAddressIsUnknown(){
        //GIVEN a person is in the list and a fireStation is in the list
        Person existingPerson = new Person("test", "test", "test", null, null, null, null);
        MedicalRecord existingMedicalRecord = new MedicalRecord(null, null, LocalDate.now(), null, null);

        when(personRepository.getAllPersons()).thenReturn(List.of(existingPerson));
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(existingMedicalRecord));
        when(fireStationRepository.getFireStation(anyString())).thenReturn(Optional.empty());

        FirePersonDTO expectedFirePersonDTOList = new FirePersonDTO(existingPerson, existingMedicalRecord);

        //WHEN we get the Fire information
        FireDTO fireDTO = fireStationService.getFire("test");

        //THEN we get the fireStation number and the person information
        assertEquals(new FireDTO("Unknown", List.of(expectedFirePersonDTOList)), fireDTO);
    }

    @Test
    public void getFloodTest(){
        //GIVEN a person is in the list
        Person existingPerson = new Person("test", "test", "testAddress", null, null, null, null);
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        when(fireStationRepository.getAddressFromStationNumber(anyInt())).thenReturn(List.of("testAddress"));
        when(personRepository.getAllPersons()).thenReturn(List.of(existingPerson));
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(existingMedicalRecord));
        FloodPersonDTO expectedFloodPersonDTO = new FloodPersonDTO(existingPerson, existingMedicalRecord);

        //WHEN we get for the flood list
        List<FloodDTO> floodDTOList = fireStationService.getFlood(List.of(1));

        //THEN the person and the address are matching
        assertEquals(List.of(new FloodDTO("testAddress", List.of(expectedFloodPersonDTO))), floodDTOList);
    }
}
