package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {
    private final DataRepositoryImpl dataRepository;

    public List<Person> getAllPersons() {
        return dataRepository.getPersons();
    }

    public boolean add(Person person) {
        if (!isDuplicate(person))
            return dataRepository.getPersons().add(person);
        return false;
    }


    public List<Person> findByAddress(String address) {
        return dataRepository.getPersons()
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();
    }


    public List<String> findByAddressAndReturnPhoneNumber(String address) {
        return dataRepository.getPersons()
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .map(person -> person.getPhone())
                .toList();
    }


    public boolean delete(String firstName, String lastName) {
        return dataRepository.getPersons().removeIf(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
    }

    public boolean isDuplicate(Person person) {
        return dataRepository.getPersons()
                .stream().anyMatch(p -> p.getFirstName().equals(person.getFirstName()) &&
                        p.getLastName().equals(person.getLastName()) &&
                        p.getAddress().equals(person.getAddress()) &&
                        p.getCity().equals(person.getCity()) &&
                        p.getZip().equals(person.getZip()) &&
                        p.getPhone().equals(person.getPhone()) &&
                        p.getEmail().equals(person.getEmail()));

    }

    public boolean update(Person person, String firstName, String lastName) {
        AtomicBoolean isDone = new AtomicBoolean(false);
        dataRepository.getPersons()
                .stream()
                .filter(i -> i.getFirstName().equals(firstName) && i.getLastName().equals(lastName))
                .findFirst()
                .ifPresent(p -> {
                    p.setAddress(person.getAddress());
                    p.setCity(person.getCity());
                    p.setZip(person.getZip());
                    p.setPhone(person.getPhone());
                    p.setEmail(person.getEmail());
                    isDone.set(true);
                });
        return isDone.get();
    }
}
