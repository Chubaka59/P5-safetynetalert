package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {
    @Autowired
    private DataRepository dataRepository = new DataRepository();

    List<Person> personList;

    public List<Person> getAllPersons(){
        personList = List.of(dataRepository.getPersonsFromFile());
        return personList;
    }
}
