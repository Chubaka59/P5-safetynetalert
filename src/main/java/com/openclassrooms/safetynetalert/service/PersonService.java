package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> getPersons();

    Person add(CreatePersonDTO person);

    Person delete(String firstName, String lastName);

    Person update(UpdatePersonDTO person, String firstName, String lastName);
}
