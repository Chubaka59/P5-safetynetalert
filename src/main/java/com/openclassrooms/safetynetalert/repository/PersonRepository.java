package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.Person;

import java.util.List;

public interface PersonRepository {
    /**
     * get all Person from DataRepository
     * @return a list of Person
     */
    List<Person> getAllPersons();

    /**
     * add a person to the personList
     * @param person the person to add
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
     * check if a person is already in the personList
     * @param person the person to check if duplicated
     * @return a boolean
     */
    boolean isDuplicate(Person person);

    /**
     * update the information of a person in the personList
     * @param person the information to be updated
     * @param firstName the firstName of the person to update
     * @param lastName the lastName of the person to update
     * @return a boolean
     */
    boolean update(Person person, String firstName, String lastName);
}
