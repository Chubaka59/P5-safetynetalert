package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PersonRepositoryTest {
    private DataRepository dataRepository = mock(DataRepository.class);
    private PersonRepository personRepository;
    private Person person;
    private CreatePersonDTO createPersonDTO;
    private UpdatePersonDTO updatePersonDTO;
    private List<Person> personList;

    @BeforeEach
    public void setupPerTest(){
        person = new Person();
        createPersonDTO = new CreatePersonDTO();
        updatePersonDTO = new UpdatePersonDTO();
        personRepository = new PersonRepositoryImpl(dataRepository);
        personList = new ArrayList<>();
    }

    @Test
    public void getAllPersonsTest(){
        //GIVEN a list should be returned
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN the method is called
        personRepository.getAllPersons();

        //THEN dataRepository.getPersons is called 1 time
        verify(dataRepository, times(1)).getPersons();
    }

    @Test
    public void getPersonTest(){
        //GIVEN a person is in the list
        person.setFirstName("test");
        person.setLastName("test");
        personList.add(person);
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN we try to find this person
        Optional<Person> optionalPerson = personRepository.getPerson("test", "test");

        //THEN this person is returned as Optional
        verify(dataRepository, times(1)).getPersons();
        assertEquals(optionalPerson.get(), person);
    }

    @Test
    public void addTest(){
        //GIVEN there is a person to add
        createPersonDTO.setFirstName("test");
        createPersonDTO.setLastName("test");
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN we add the person
        person = personRepository.add(createPersonDTO);

        //THEN the person has been added to the list
        assertEquals(personList.get(0), person);
    }

    @Test
    public void deleteTest(){
        //GIVEN there is a person to delete in the personList
        person.setFirstName("test");
        person.setLastName("test");
        personList.add(person);
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN we delete the person
        person = personRepository.delete(person);

        //THEN the person isn't in the list anymore
        assertEquals(personList.size(), 0);
    }

    @Test
    public void updateTest(){
        //GIVEN there is a person to update in the personList and update information
        person.setFirstName("test");
        person.setLastName("test");
        personList.add(person);

        when(dataRepository.getPersons()).thenReturn(personList);

        updatePersonDTO.setAddress("test");
        updatePersonDTO.setZip("test");
        updatePersonDTO.setCity("test");
        updatePersonDTO.setPhone("test");
        updatePersonDTO.setEmail("test");

        //WHEN the person is updated
        person = personRepository.update(person, updatePersonDTO);

        //THEN the information of the person are updated
        assertEquals(personList.get(0), person);
    }
}
