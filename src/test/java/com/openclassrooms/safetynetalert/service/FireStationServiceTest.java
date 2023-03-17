package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FireStationServiceTest {
    private FireStationRepository fireStationRepository = mock(FireStationRepository.class);
    private FireStationServiceImpl fireStationService;
    private FireStation fireStation;
    private CreateFireStationDTO createFireStationDTO = mock(CreateFireStationDTO.class);
    private UpdateFireStationDTO updateFireStationDTO;
    private MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
    private PersonRepository personRepository = mock(PersonRepository.class);
    private Person person;

    @BeforeEach
    public void setupPerTest(){
        fireStation = new FireStation();
        fireStationService = new FireStationServiceImpl(fireStationRepository, personRepository, medicalRecordRepository);
        person = new Person();
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

    @Test
    public void getPersonsFromFireStationTest(){
        //GIVEN there is a minor person in the list
        List<String> addresses = new ArrayList<>();
        addresses.add("test");
        List<Person> personList = new ArrayList<>();
        MedicalRecord medicalRecord = new MedicalRecord();

        person.setFirstName("test");
        person.setLastName("test");
        medicalRecord.setBirthdate(LocalDate.now());
        personList.add(person);

        when(fireStationRepository.getAddressFromStationNumber(anyInt())).thenReturn(addresses);
        when(personRepository.findByAddress("test")).thenReturn(personList);
        when(medicalRecordRepository.getMedicalRecord("test", "test")).thenReturn(Optional.of(medicalRecord));

        //WHEN we create the DTO
        FireStationDTO fireStationDTO = fireStationService.getPersonsFromFireStation(1);

        //THEN those methods are called and the minor is in it
        verify(fireStationRepository, times(1)).getAddressFromStationNumber(anyInt());
        verify(personRepository, times(1)).findByAddress(anyString());
        verify(medicalRecordRepository, times(1)).getMedicalRecord(anyString(), anyString());

        assertEquals(fireStationDTO.getNumberOfMinor(), 1);
        assertEquals(fireStationDTO.getPersonDTOList().get(0).getFirstName(), person.getFirstName());
    }

    @Test
    public void getPhoneAlertTest(){
        //GIVEN it should return an addressList and a personList
        List<String> addresses = new ArrayList<>();
        addresses.add("test");
        List<Person> personList = new ArrayList<>();
        person.setPhone("0123456789");
        personList.add(person);
        when(fireStationRepository.getAddressFromStationNumber(anyInt())).thenReturn(addresses);
        when(personRepository.findByAddress(anyString())).thenReturn(personList);

        //WHEN we request for the phoneAlert
        List<PhoneAlertDTO> phoneAlertDTOList = fireStationService.getPhoneAlert(1);

        //THEN a list is returned with person's phone number
        assertEquals(1, phoneAlertDTOList.size());
        assertEquals(person.getPhone(), phoneAlertDTOList.get(0).getPhone());
    }
}
