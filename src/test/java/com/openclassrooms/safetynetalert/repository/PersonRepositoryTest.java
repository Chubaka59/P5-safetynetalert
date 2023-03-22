package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PersonRepositoryTest {
    private DataRepository dataRepository = mock(DataRepository.class);
    @MockBean
    private PersonRepository personRepository;

    @BeforeEach
    public void setupPerTest(){
        personRepository = new PersonRepositoryImpl(dataRepository);
    }

    @Test
    public void getAllPersonsTest(){
        //GIVEN a list should be returned
        Person existingPerson = new Person();
        when(dataRepository.getPersons()).thenReturn(List.of(existingPerson));

        //WHEN the method is called
        personRepository.getAllPersons();

        //THEN dataRepository.getPersons is called 1 time
        verify(dataRepository, times(1)).getPersons();
    }

    @Test
    public void getPersonTest(){
        //GIVEN a person is in the list
        Person existingPerson = new Person("test", "test", null, null, null, null, null);
        when(dataRepository.getPersons()).thenReturn(List.of(existingPerson));

        //WHEN we try to find this person
        Optional<Person> optionalPerson = personRepository.getPerson("test", "test");

        //THEN this person is returned as Optional
        verify(dataRepository, times(1)).getPersons();
        assertEquals(Optional.of(existingPerson), optionalPerson);
    }

    @Test
    public void addTest(){
        //GIVEN there is a person to add
        List<Person> personList = new ArrayList<>();
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN we add the person
        Person addedPerson = personRepository.add(new CreatePersonDTO("test", "test", null, null, null, null, null));

        //THEN the person has been added to the list
        assertEquals(personList.get(0), addedPerson);
    }

    @Test
    public void deleteTest(){
        //GIVEN there is a person to delete in the personList
        Person existingPerson = new Person("test", "test", null, null, null, null, null);
        List<Person> personList = new ArrayList<>();
        personList.add(existingPerson);
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN we delete the person
        Person deletedPerson = personRepository.delete(existingPerson);

        //THEN the person isn't in the list anymore
        assertEquals(existingPerson, deletedPerson);
        assertEquals(0, personList.size());
    }

    @Test
    public void updateTest(){
        //GIVEN there is a person to update in the personList and update information
        Person existingPerson = new Person("test", "test", null, null, null, null, null);
        UpdatePersonDTO updatePersonDTO = new UpdatePersonDTO("testAddress", "testCity", "testZip", "testPhone", "testMail");
        when(dataRepository.getPersons()).thenReturn(List.of(existingPerson));

        Person expectedPerson = new Person("test", "test", "testAddress", "testCity", "testZip", "testPhone", "testMail");

        //WHEN the person is updated
        Person updatedperson = personRepository.update(existingPerson, updatePersonDTO);

        //THEN the information of the person are updated
        assertEquals(expectedPerson, updatedperson);
    }

    @Test
    public void findByAddressTest(){
        //GIVEN a person already exist in the list
        Person existingPerson = new Person("test", "test", "testAddress", null, null, null, null);
        when(dataRepository.getPersons()).thenReturn(List.of(existingPerson));

        //WHEN we search for this address
        List<Person> personList = personRepository.findByAddress("testAddress");

        //THEN the existing person is found
        assertEquals(List.of(existingPerson), personList);

    }
}
