package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> getAllPersons();

    Optional<Person> getPerson(String firstName, String lastName);


    Person add(CreatePersonDTO person);

    Person delete(Person person);


    List<Person> getPersonsFromAddressList(List<String> addresses);


    Person update(Person person, UpdatePersonDTO personDTO);
}
