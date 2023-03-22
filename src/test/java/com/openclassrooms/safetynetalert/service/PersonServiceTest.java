package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.childAlert.ChildAlertDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MajorPersonDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MinorPersonDTO;
import com.openclassrooms.safetynetalert.dto.communityemail.CommunityEmailDTO;
import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.exception.person.PersonAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.person.PersonNotFoundException;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    private PersonRepository personRepository = mock(PersonRepository.class);
    private MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
    @MockBean
    private PersonServiceImpl personService;

    @BeforeEach
    public void setupPerTest(){
        person = new Person();
        medicalRecord = new MedicalRecord();
        personService = new PersonServiceImpl(personRepository, medicalRecordRepository);
    }

    @Test
    public void getPersonsTest(){
        //GIVEN a list should be returned
        List<Person> personList = new ArrayList<>();
        when(personRepository.getAllPersons()).thenReturn(personList);

        //WHEN the method is called
        personService.getPersons();

        //THEN personRepository.getAllPersons is called 1 time
        verify(personRepository, times(1)).getAllPersons();

    }

    @Test
    public void addTest(){
        //GIVEN the person we would add doesn't exist
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.empty());

        //WHEN we would add the person
        personService.add(new CreatePersonDTO());

        //THEN personRepository.add is called 1 time
        verify(personRepository, times(1)).add(any(CreatePersonDTO.class));
    }

    @Test
    public void addWhenPersonAlreadyExistTest(){
        //GIVEN the person we would add already exist
        CreatePersonDTO addExistingPerson = new CreatePersonDTO("test", "test", null, null, null, null, null);
        Person existingPerson = new Person("test", "test", null, null, null, null, null);

        when(personRepository.getPerson(addExistingPerson.getFirstName(),addExistingPerson.getFirstName())).thenReturn(Optional.of(existingPerson));

        //WHEN we would add the person THEN an exception is raised
        assertThrows(PersonAlreadyExistException.class, () -> personService.add(addExistingPerson));
    }

    @Test
    public void deleteTest(){
        //GIVEN the person we would delete is found
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.of(person));

        //WHEN we would delete
        personService.delete(anyString(), anyString());

        //THEN personRepository.delete is called 1 time
        verify(personRepository, times(1)).delete(any(Person.class));
    }

    @Test
    public void deleteWhenPersonIsNotFoundTest(){
        //GIVEN the person we would delete is found
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.empty());

        //WHEN we would delete the person THEN an exception is raised
        assertThrows(PersonNotFoundException.class, () -> personService.delete(anyString(),anyString()));
    }

    @Test
    public void updateTest(){
        //GIVEN the person we would update is found
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.of(person));

        //WHEN we would update
        personService.update(updatePersonDTO, anyString(), anyString());

        //THEN personRepository.update is called 1 time
        verify(personRepository, times(1)).update(person, updatePersonDTO);
    }

    @Test
    public void updateWhenPersonIsNotFoundTest(){
        //GIVEN the person we would delete is not found
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.empty());

        //WHEN we would update the person THEN an exception is raised
        assertThrows(PersonNotFoundException.class, () -> personService.update(updatePersonDTO, anyString(), anyString()));
    }

    @Test
    public void getChildAlertTest(){
        person.setFirstName("test1");
        person.setLastName("test1");
        medicalRecord.setBirthdate(LocalDate.now());
        MinorPersonDTO minorPersonDTO = new MinorPersonDTO(person, medicalRecord);
        List<MinorPersonDTO> minorPersonDTOList = new ArrayList<>();
        minorPersonDTOList.add(minorPersonDTO);

        Person person2 = new Person();
        person2.setLastName("test2");
        person2.setLastName("test2");
        MajorPersonDTO majorPersonDTO = new MajorPersonDTO(person2);
        List<MajorPersonDTO> majorPersonDTOList = new ArrayList<>();
        majorPersonDTOList.add(majorPersonDTO);

        when(personService.getMinorPersonDTOList("test")).thenReturn(minorPersonDTOList);
        when(personService.getMajorPersonDTOList(anyString())).thenReturn(majorPersonDTOList);


        ChildAlertDTO childAlertDTO = personService.getChildAlert("test");


        //THEN
        assertEquals(List.of(new MajorPersonDTO(personMajor)), childAlertDTO.getMajorPersonDTOList());
        assertEquals(List.of(new MinorPersonDTO(personMinor, medicalRecordMinor)), childAlertDTO.getMinorPersonDTOList());
    }

    @Test
    public void getMinorPersonDTOListTest(){
        //GIVEN a minor person is in the lists
        person.setFirstName("test");
        person.setLastName("test");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("test");
        medicalRecord.setLastName("test");
        medicalRecord.setBirthdate(LocalDate.now().minusYears(2));
        when(personRepository.findByAddress(anyString())).thenReturn(personList);
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        //WHEN we try to find this person
        List<MinorPersonDTO> minorPersonDTOList = personService.getMinorPersonDTOList("test");

        //THEN we find it in the list
        assertEquals(person.getFirstName(), minorPersonDTOList.get(0).getFirstName());
        assertEquals(1, minorPersonDTOList.size());
    }

    @Test
    public void getMinorPersonDTOListWhenNoMinorTest(){
        //GIVEN a minor person is in the lists
        person.setFirstName("test");
        person.setLastName("test");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("test");
        medicalRecord.setLastName("test");
        medicalRecord.setBirthdate(LocalDate.now().minusYears(20));
        when(personRepository.findByAddress(anyString())).thenReturn(personList);
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        //WHEN we try to find this person
        List<MinorPersonDTO> minorPersonDTOList = personService.getMinorPersonDTOList("test");

        //THEN we find it in the list
        assertEquals(0, minorPersonDTOList.size());
    }

    @Test
    public void getMajorPersonDTOListTest(){
        //GIVEN a major person is in the lists
        person.setFirstName("test");
        person.setLastName("test");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("test");
        medicalRecord.setLastName("test");
        medicalRecord.setBirthdate(LocalDate.now().minusYears(20));
        when(personRepository.findByAddress(anyString())).thenReturn(personList);
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        //WHEN we try to find this person
        List<MajorPersonDTO> majorPersonDTOList = personService.getMajorPersonDTOList("test");

        //THEN we find it in the list
        assertEquals(person.getFirstName(), majorPersonDTOList.get(0).getFirstName());
        assertEquals(1, majorPersonDTOList.size());
    }

    @Test
    public void getMajorPersonDTOListWhenNoMinorTest(){
        //GIVEN a minor person is in the lists
        person.setFirstName("test");
        person.setLastName("test");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("test");
        medicalRecord.setLastName("test");
        medicalRecord.setBirthdate(LocalDate.now().minusYears(2));
        when(personRepository.findByAddress(anyString())).thenReturn(personList);
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        //WHEN we try to find this person
        List<MajorPersonDTO> majorPersonDTOList = personService.getMajorPersonDTOList("test");

        //THEN we find it in the list
        assertEquals(0, majorPersonDTOList.size());
    }

    @Test
    public void getCommunityEmailTest(){
        //GIVEN a person is in the list
        person.setEmail("testAddress");
        person.setCity("testCity");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(personRepository.getAllPersons()).thenReturn(personList);

        //WHEN we get the emails
        List<CommunityEmailDTO> communityEmailDTOList = personService.getCommunityEmail("testCity");

        //THEN we find the mail of this person
        assertEquals(person.getEmail(), communityEmailDTOList.get(0).getMail());
    }
}
