package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.impl.PersonRepositoryImpl;
import com.openclassrooms.safetynetalert.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepositoryImpl personRepository;

    public List<Person> getPersons(){
        return personRepository.getAllPersons();
    }

    public boolean add(Person person) {
        return personRepository.add(person);
    }

    public boolean delete(String firstName, String lastName) {
        return personRepository.delete(firstName, lastName);
    }

    public boolean update(Person person, String firstName, String lastName) {
        return personRepository.update(person, firstName, lastName);
    }
}
