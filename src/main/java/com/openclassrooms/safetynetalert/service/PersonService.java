package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    PersonRepository personRepository = new PersonRepository();

    public List<Person> getPersons(){
        return personRepository.getAllPersons();
    }

    public void addPerson(Person person) {
        personRepository.addAPersonInList(person);
    }
}
