package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.DataRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {
    private final DataRepository dataRepository;

    @Override
    public List<Person> getAllPersons() {
        return dataRepository.getPersons();
    }

    @Override
    public Optional<Person> getPerson(String firstName, String lastName){
        return dataRepository.getPersons()
                .stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst();
    }

    @Override
    public Person add(CreatePersonDTO personDTO){
        dataRepository.getPersons().add(new Person(personDTO));
        return getPerson(personDTO.getFirstName(), personDTO.getLastName()).orElseThrow();
    }

    @Override
    public Person delete(Person person) {
        dataRepository.getPersons().removeIf(p -> p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()));
        return person;
    }

    @Override
    public Person update(Person person, UpdatePersonDTO personDTO){
        dataRepository.getPersons()
                .stream()
                .filter(p -> p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()))
                .findFirst()
                .ifPresent(p -> p.update(personDTO));
        return getPerson(person.getFirstName(), person.getLastName()).orElseThrow();
    }

    @Override
    public List<Person> findByAddress(String address) {
        return dataRepository.getPersons()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .toList();
    }
}
