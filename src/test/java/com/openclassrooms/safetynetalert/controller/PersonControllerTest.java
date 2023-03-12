package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    MockMvc mockMvc;
    PersonServiceImpl personService = Mockito.mock(PersonServiceImpl.class);
    PersonController personController;
    Person person;
    @BeforeEach
    public void setupPerTest(){
        person = new Person();
        personController = new PersonController(personService);
    }

    @Test
    public void findAllPersonsTest() throws Exception {
        //WHEN the request to find all persons is sent
        personController.findAllPersons();

        //THEN personService.getPersons is called
        verify(personService, times(1)).getPersons();
    }

    @Test
    public void addTest() throws Exception {
        //GIVEN we need to add a person
        given(personService.add(any(Person.class))).willReturn(true);

        //WHEN we post a new person
        ResponseEntity<Person> response = personController.add(person);

        //THEN the person is createds
        then(personService).should().add(person);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addWhenAFieldIsMissing() throws Exception {
        //TODO this is an IT, need to move it in IT Test
        String body = "{ \"lastName\":\"Test\", \"address\":\"Test\", \"city\":\"Test\", \"zip\":\"Test\", \"phone\":\"Test\", \"email\":\"Test@test\" }";
        when(personService.add(any(Person.class))).thenReturn(true);

        //WHEN we post a new person
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/persons")
                .accept(MediaType.APPLICATION_JSON).content(body)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        //THEN the person is created on the correct address
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void addWhenPersonAlreadyExistTest() {
        //GIVEN the person to add already exist
        given(personService.add(any(Person.class))).willReturn(false);

        //WHEN we add the person
        ResponseEntity<Person> response = personController.add(person);

        //THEN the response status is conflict
        then(personService).should().add(person);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteTest() {
        //GIVEN a person needs to be deleted
        given(personService.delete(anyString(),anyString())).willReturn(true);

        //WHEN we request to delete the person
        ResponseEntity<Person> response = personController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is OK
        then(personService).should().delete(anyString(), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteNotExistingPersonTest() {
        //GIVEN a person that doesn't exist will be deleted
        given(personService.delete(anyString(), anyString())).willReturn(false);

        //WHEN we request to delete the person
        ResponseEntity<Person> response = personController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is NOT_FOUND
        then(personService).should().delete(anyString(), anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTest() {
        //GIVEN a person will be updated
        given(personService.update(any(Person.class), anyString(), anyString())).willReturn(true);

        //WHEN we update the person
        ResponseEntity<Person> response = personController.update(person, "test", "test");

        //THEN the method to update is called and the response is OK
        then(personService).should().update(any(Person.class), anyString(), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateNotExistingPersonTest(){
        //GIVEN the person we would update doesn't exist
        given(personService.update(any(Person.class), anyString(), anyString())).willReturn(false);

        //WHEN we update the person
        ResponseEntity<Person> response = personController.update(person, "test", "test");

        //THEN the response is NOT_FOUND
        then(personService).should().update(any(Person.class), anyString(), anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
