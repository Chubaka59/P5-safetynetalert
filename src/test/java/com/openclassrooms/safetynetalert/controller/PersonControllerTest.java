package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.dto.childAlert.ChildAlertDTO;
import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.dto.personinfodto.PersonInfoDTO;
import com.openclassrooms.safetynetalert.exception.person.PersonAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.person.PersonNotFoundException;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonControllerTest {
    private PersonService personService = mock(PersonService.class);
    @MockBean
    private PersonController personController;

    @BeforeEach
    public void setupPerTest(){
        personController = new PersonController(personService);
    }

    @Test
    public void findAllPersonsTest() {
        //WHEN the request to find all persons is sent
        personController.findAllPersons();

        //THEN personService.getPersons is called
        verify(personService, times(1)).getPersons();
    }

    @Test
    public void addTest() {
        //GIVEN we need to add a person
        given(personService.add(any(CreatePersonDTO.class))).willReturn(any(Person.class));

        //WHEN we post a new person
        ResponseEntity<Person> response = personController.add(new CreatePersonDTO());

        //THEN the person is created
        then(personService).should().add(any(CreatePersonDTO.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addWhenPersonAlreadyExistTest() {
        //GIVEN the person to add already exist
        given(personService.add(any(CreatePersonDTO.class))).willThrow(new PersonAlreadyExistException(anyString(), anyString()));

        //WHEN we add the person
        ResponseEntity<Person> response = personController.add(new CreatePersonDTO());

        //THEN the response status is conflict
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteTest() {
        //GIVEN a person needs to be deleted
        Person existingPerson = new Person();
        given(personService.delete(anyString(), anyString())).willReturn(existingPerson);

        //WHEN we request to delete the person
        ResponseEntity<Person> response = personController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is OK
        then(personService).should().delete(anyString(), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void deleteNotExistingPersonTest() {
        //GIVEN a person that doesn't exist will be deleted
        given(personService.delete(anyString(), anyString())).willThrow(new PersonNotFoundException(anyString(), anyString()));

        //WHEN we request to delete the person
        ResponseEntity<Person> response = personController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is NOT_FOUND
        then(personService).shouldHaveNoInteractions();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTest() {
        //GIVEN a person will be updated
        Person existingPerson = new Person();
        given(personService.update(new UpdatePersonDTO(), "test", "Test")).willReturn(existingPerson);

        //WHEN we update the person
        ResponseEntity<Person> response = personController.update(new UpdatePersonDTO(), "test", "Test");

        //THEN the method to update is called and the response is OK
        then(personService).should().update(new UpdatePersonDTO(), "test", "Test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateNotExistingPersonTest(){
        //GIVEN the person we would update doesn't exist
        given(personService.update(any(UpdatePersonDTO.class), anyString(), anyString())).willThrow(new PersonNotFoundException(anyString(), anyString()));

        //WHEN we update the person
        ResponseEntity<Person> response = personController.update(any(UpdatePersonDTO.class), anyString(), anyString());

        //THEN the response is NOT_FOUND
        then(personService).shouldHaveNoInteractions();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getChildAlert(){
        //GIVEN this should return a ChildAlertDTO
        when(personService.getChildAlert(anyString())).thenReturn(any(ChildAlertDTO.class));

        //WHEN the method is called
        personController.getChildAlert("test");

        //THEN personService.getChildAlert is called once
        verify(personService, times(1)).getChildAlert(anyString());
    }

    @Test
    public void getPersonInfoTest(){
        //GIVEN this should return a list
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        when(personService.getPersonInfo(any(Optional.class), anyString())).thenReturn(List.of(new PersonInfoDTO(new Person(), existingMedicalRecord)));

        //WHEN the method is called
        personController.getPersonInfo(Optional.of("test"), "test");

        //THEN personService.getPersonInfo is called once
        verify(personService, times(1)).getPersonInfo(any(Optional.class), anyString());
    }

    @Test
    public void getCommunityEmailTest(){
        //GIVEN this should return a list
        when(personService.getCommunityEmail(anyString())).thenReturn(anyList());

        //WHEN the method is called
        personController.getCommunityEmail("test");

        //THEN personService.getCommunityEmail is called once
        verify(personService, times(1)).getCommunityEmail(anyString());
    }
}
