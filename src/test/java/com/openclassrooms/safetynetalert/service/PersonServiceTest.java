package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.impl.PersonRepositoryImpl;
import com.openclassrooms.safetynetalert.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PersonServiceTest {
    PersonRepositoryImpl personRepository = mock(PersonRepositoryImpl.class);
    PersonServiceImpl personService;
    Person person;

    @BeforeEach
    public void setupPerTest(){
        person = new Person();
        personService = new PersonServiceImpl(personRepository);
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
        //GIVEN when the method is called, it should return a boolean
        when(personRepository.add(any(Person.class))).thenReturn(true);

        //WHEN the method is called
        personService.add(person);

        //THEN personRepository.add is called 1 time
        verify(personRepository, times(1)).add(any(Person.class));
    }

    @Test
    public void deleteTest(){
        //GIVEN when the method is called, it should return a boolean
        when(personRepository.delete(anyString(), anyString())).thenReturn(true);

        //WHEN the method is called
        personService.delete(anyString(), anyString());

        //THEN personRepository.delete is called 1 time
        verify(personRepository, times(1)).delete(anyString(), anyString());
    }

    @Test
    public void updateTest(){
        //GIVEN when the method is called, it should return a boolean
        when(personRepository.update(any(Person.class), anyString(), anyString())).thenReturn(true);

        //WHEN the method is called
        personService.update(person, "test", "test");

        //THEN personRepository.update is called 1 time
        verify(personRepository, times(1)).update(any(Person.class), anyString(), anyString());
    }
}
