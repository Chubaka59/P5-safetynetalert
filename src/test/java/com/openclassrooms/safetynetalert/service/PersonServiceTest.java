package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.childAlert.ChildAlertDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MajorPersonDTO;
import com.openclassrooms.safetynetalert.dto.childAlert.MinorPersonDTO;
import com.openclassrooms.safetynetalert.dto.communityemail.CommunityEmailDTO;
import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.dto.personinfodto.PersonInfoDTO;
import com.openclassrooms.safetynetalert.exception.person.PersonAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.person.PersonNotFoundException;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    private PersonRepository personRepository = mock(PersonRepository.class);
    private MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
    @MockBean
    private PersonServiceImpl personService;

    @BeforeEach
    public void setupPerTest(){
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
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.of(new Person()));

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
        Person existingPerson = new Person("test", "test", null, null, null, null, null);
        UpdatePersonDTO updatedPerson = new UpdatePersonDTO();
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.of(existingPerson));

        //WHEN we would update
        personService.update(updatedPerson, anyString(), anyString());

        //THEN personRepository.update is called 1 time
        verify(personRepository, times(1)).update(existingPerson, updatedPerson);
    }

    @Test
    public void updateWhenPersonIsNotFoundTest(){
        //GIVEN the person we would delete is not found
        when(personRepository.getPerson(anyString(), anyString())).thenReturn(Optional.empty());
        UpdatePersonDTO updatedPerson = new UpdatePersonDTO();

        //WHEN we would update the person THEN an exception is raised
        assertThrows(PersonNotFoundException.class, () -> personService.update(updatedPerson, anyString(), anyString()));
    }

    @Test
    public void getChildListFromAddressTest(){
        //GIVEN a major person
        Person personMinor = new Person();
        personMinor.setFirstName("test1");
        personMinor.setLastName("test1");

        MedicalRecord medicalRecordMinor= new MedicalRecord();
        medicalRecordMinor.setFirstName(personMinor.getFirstName());
        medicalRecordMinor.setLastName(personMinor.getLastName());
        medicalRecordMinor.setBirthdate(LocalDate.now());

        // AND a minor person
        Person personMajor = new Person();
        personMajor.setLastName("test2");
        personMajor.setLastName("test2");

        MedicalRecord medicalRecordMajor= new MedicalRecord();
        medicalRecordMajor.setFirstName(personMajor.getFirstName());
        medicalRecordMajor.setLastName(personMajor.getLastName());
        medicalRecordMajor.setBirthdate(LocalDate.parse("1986-08-16"));

        // AND a repository expected response
        final List<Person> persons = List.of(personMajor, personMinor);

        //WHEN

        when(personRepository.findByAddress(anyString())).thenReturn(persons);

        when(medicalRecordRepository.getMedicalRecord(personMajor.getFirstName(), personMajor.getLastName())).thenReturn(Optional.of(medicalRecordMajor));
        when(medicalRecordRepository.getMedicalRecord(personMinor.getFirstName(), personMinor.getLastName())).thenReturn(Optional.of(medicalRecordMinor));


        ChildAlertDTO childAlertDTO = personService.getChildListFromAddress("test");


        //THEN
        assertEquals(List.of(new MajorPersonDTO(personMajor)), childAlertDTO.getMajorPersonDTOList());
        assertEquals(List.of(new MinorPersonDTO(personMinor, medicalRecordMinor)), childAlertDTO.getMinorPersonDTOList());
    }

    @Test
    public void getMinorPersonDTOListTest(){
        //GIVEN a minor person is in the lists
        Person minorPerson = new Person();
        minorPerson.setFirstName("test");
        minorPerson.setLastName("test");

        MedicalRecord minorMedicalRecord = new MedicalRecord();
        minorMedicalRecord.setBirthdate(LocalDate.now().minusYears(2));

        when(personRepository.findByAddress(anyString())).thenReturn(List.of(minorPerson));
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(minorMedicalRecord));

        //WHEN we try to find this person
        List<MinorPersonDTO> minorPersonDTOList = personService.getMinorPersonDTOList("test");

        //THEN we find it in the list
        assertEquals(List.of(new MinorPersonDTO(minorPerson, minorMedicalRecord)), minorPersonDTOList);
    }

    @Test
    public void getMinorPersonDTOListWhenNoMinorTest(){
        //GIVEN a minor person is in the lists
        Person majorPerson = new Person();
        majorPerson.setFirstName("test");
        majorPerson.setLastName("test");

        MedicalRecord majorMedicalRecord = new MedicalRecord();
        majorMedicalRecord.setBirthdate(LocalDate.now().minusYears(20));

        when(personRepository.findByAddress(anyString())).thenReturn(List.of(majorPerson));
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(majorMedicalRecord));

        //WHEN we try to find this person
        List<MinorPersonDTO> minorPersonDTOList = personService.getMinorPersonDTOList("test");

        //THEN we find it in the list
        assertNotEquals(List.of(new MinorPersonDTO(majorPerson, majorMedicalRecord)), minorPersonDTOList);
        assertEquals(0, minorPersonDTOList.size());
    }

    @Test
    public void getMajorPersonDTOListTest(){
        //GIVEN a major person is in the lists
        Person majorPerson = new Person();
        majorPerson.setFirstName("test");
        majorPerson.setLastName("test");

        MedicalRecord majorMedicalRecord = new MedicalRecord();
        majorMedicalRecord.setBirthdate(LocalDate.now().minusYears(20));

        when(personRepository.findByAddress(anyString())).thenReturn(List.of(majorPerson));
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(majorMedicalRecord));

        //WHEN we try to find this person
        List<MajorPersonDTO> majorPersonDTOList = personService.getMajorPersonDTOList("test");

        //THEN we find it in the list
        assertEquals(List.of(new MajorPersonDTO(majorPerson)), majorPersonDTOList);
    }

    @Test
    public void getMajorPersonDTOListWhenNoMajorTest(){
        //GIVEN a minor person is in the lists
        Person minorPerson = new Person();
        minorPerson.setFirstName("test");
        minorPerson.setLastName("test");

        MedicalRecord minorMedicalRecord = new MedicalRecord();
        minorMedicalRecord.setBirthdate(LocalDate.now().minusYears(2));

        when(personRepository.findByAddress(anyString())).thenReturn(List.of(minorPerson));
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(minorMedicalRecord));

        //WHEN we try to find this person
        List<MajorPersonDTO> majorPersonDTOList = personService.getMajorPersonDTOList("test");

        //THEN we find it in the list
        assertNotEquals(List.of(new MajorPersonDTO(minorPerson)), majorPersonDTOList);
        assertEquals(0, majorPersonDTOList.size());
    }

    @Test
    public void getPersonListFromFirstnameAndLastnameWhenFirstNameIsSpecifiedTest(){
        //GIVEN 2 different persons with the same lastName are in the list
        Person existingPerson1 = new Person("test", "test", null, null, null, null, null);
        Person existingPerson2 = new Person("test2", "test", null, null, null, null, null);
        when(personRepository.getAllPersons()).thenReturn(List.of(existingPerson1, existingPerson2));
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(existingMedicalRecord));

        //WHEN we get the information of the person1
        List<PersonInfoDTO> personInfoDTOList = personService.getPersonListFromFirstnameAndLastnameList(Optional.of("test"), "test");

        //THEN a list is created with the person information
        assertEquals(List.of(new PersonInfoDTO(existingPerson1, existingMedicalRecord)), personInfoDTOList);
    }

    @Test
    public void getPersonListFromFirstnameAndLastnameWhenFirstNameIsNotSpecifiedTest(){
        //GIVEN 2 different persons with the same lastName are in the list
        Person existingPerson1 = new Person("test", "test", null, null, null, null, null);
        Person existingPerson2 = new Person("test2", "test", null, null, null, null, null);
        when(personRepository.getAllPersons()).thenReturn(List.of(existingPerson1, existingPerson2));
        MedicalRecord existingMedicalRecord1 = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        MedicalRecord existingMedicalRecord2 = new MedicalRecord("test2", "test", LocalDate.now(), null, null);
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(existingMedicalRecord1));

        List<PersonInfoDTO> expectedList = List.of(new PersonInfoDTO(existingPerson1, existingMedicalRecord1), new PersonInfoDTO(existingPerson2, existingMedicalRecord2));

        //WHEN we get the information of both persons
        List<PersonInfoDTO> personInfoDTOList = personService.getPersonListFromFirstnameAndLastnameList(Optional.empty(), "test");

        //THEN a list is created with the persons information
        assertEquals(expectedList, personInfoDTOList);
    }

    @Test
    public void getEmailListFromCityTest(){
        //GIVEN a person is in the list
        Person person = new Person();
        person.setEmail("testAddress");
        person.setCity("testCity");

        when(personRepository.getAllPersons()).thenReturn(List.of(person));

        //WHEN we get the emails
        List<CommunityEmailDTO> communityEmailDTOList = personService.getEmailListFromCity("testCity");

        //THEN we find the mail of this person
        assertEquals(List.of(new CommunityEmailDTO(person)), communityEmailDTOList);
    }
}
