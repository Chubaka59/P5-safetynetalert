package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.exception.person.PersonAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.person.PersonNotFoundException;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    private PersonRepository personRepository = mock(PersonRepository.class);
    private PersonService personService;
    private Person person;
    private CreatePersonDTO createPersonDTO = mock(CreatePersonDTO.class);
    private UpdatePersonDTO updatePersonDTO;

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
        //GIVEN the person we would add doesn't exist
        when(personRepository.getPerson(createPersonDTO.getFirstName(), createPersonDTO.getLastName())).thenReturn(Optional.empty());

        //WHEN we would add the person
        personService.add(createPersonDTO);

        //THEN personRepository.add is called 1 time
        verify(personRepository, times(1)).add(any(CreatePersonDTO.class));
    }

    @Test
    public void addWhenPersonAlreadyExistTest(){
        //GIVEN the person we would add already exist
        when(personRepository.getPerson(createPersonDTO.getFirstName(), createPersonDTO.getLastName())).thenReturn(Optional.of(person));

        //WHEN we would add the person THEN an exception is raised
        assertThrows(PersonAlreadyExistException.class, () -> personService.add(createPersonDTO));
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
}
