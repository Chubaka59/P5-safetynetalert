package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.Person;

import java.util.List;

public interface PersonService {

    /**
     * get personList from repository
     * @return a list of person
     */
    List<Person> getPersons();

    /**
     * add a person to personList
     * @param person person to add
     * @return a boolean
     */
    boolean add(Person person);

    /**
     * delete a person from the personList
     * @param firstName the firstName of the person to delete
     * @param lastName the lastName of the person to delete
     * @return a boolean
     */
    boolean delete(String firstName, String lastName);

    /**
     * update the information of a person in the personList
     * @param person the information to be updated
     * @param firstName the firstName of the person to update
     * @param lastName the lastName of the person to update
     * @return boolean
     */
    boolean update(Person person, String firstName, String lastName);
}
