package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    /**
     * Get a list of all persons
     * @return a list of persons
     */
    List<Person> getAllPersons();

    /**
     * Get a person from its firstName and lastName
     * @param firstName the firstName to filter the person
     * @param lastName the lastName to filter the person
     * @return an Optional of the person
     */
    Optional<Person> getPerson(String firstName, String lastName);

    /**
     * Add a person to the personList
     * @param personDTO the information of the person to add
     * @return the person that has been added or throw an Exception
     */
    Person add(CreatePersonDTO personDTO);

    /**
     * Delete a pêrson from the personList
     * @param person the person to delete
     * @return the person that has been deleted
     */
    Person delete(Person person);

    /**
     * Update the information of a person
     * @param person the person to update
     * @param personDTO the personDTO to get the information to update
     * @return the person that has been updated or throw an Exception
     */
    Person update(Person person, UpdatePersonDTO personDTO);

    List<Person> getPersonsFromAddressList(List<String> addresses);
}
