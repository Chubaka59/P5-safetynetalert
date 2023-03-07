package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {
    public static List<Person> personList;

    public List<Person> getAllPersons(){
        return personList;
    }

    public void addAPersonInList(Person person) {
        personList.add(person);
    }
}
