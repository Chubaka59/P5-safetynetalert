package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;

import java.util.List;

public interface PersonService {
    /**
     * Get a list of all persons
     * @return a list of persons
     */
    List<Person> getPersons();

    /**
     * Check if the person to add exists then call the method to add it or throw an Exception
     * @param personDTO the information of the person to add
     * @return the person that has been added
     */
    Person add(CreatePersonDTO personDTO);

    /**
     * Check if the person to delete exists then call the method to delete or throw an Exception
     * @param firstName the firstName of the person to delete
     * @param lastName the lastName of the person to delete
     * @return the person that has been deleted
     */
    Person delete(String firstName, String lastName);

    /**
     * Check if the person to update exists then call the method to update or throw an Exception
     * @param personDTO the information of the person to update
     * @param firstName the firstName of the person to update
     * @param lastName the lastName of the person to update
     * @return the person that has been updated
     */
    Person update(UpdatePersonDTO personDTO, String firstName, String lastName);
}
