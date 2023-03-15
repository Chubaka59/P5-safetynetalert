package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.exception.person.PersonAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.person.PersonNotFoundException;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import com.openclassrooms.safetynetalert.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public List<Person> getPersons(){
        return personRepository.getAllPersons();
    }

    @Override
    public Person add(CreatePersonDTO personDTO) {
        if(personRepository.getPerson(personDTO.getFirstName(), personDTO.getLastName()).isPresent())
            throw new PersonAlreadyExistException(personDTO.getFirstName(), personDTO.getLastName());
        return personRepository.add(personDTO);
    }

    @Override
    public Person delete(String firstName, String lastName) {
        Person person = personRepository.getPerson(firstName, lastName)
                .orElseThrow(() -> new PersonNotFoundException(firstName, lastName));
        return personRepository.delete(person);
    }

    @Override
    public Person update(UpdatePersonDTO personDTO, String firstName, String lastName) {
        Person person = personRepository.getPerson(firstName, lastName)
                .orElseThrow(() -> new PersonNotFoundException(firstName, lastName));
        return personRepository.update(person, personDTO);
    }
}
