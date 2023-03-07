package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class PersonController {
    PersonService personService = new PersonService();
    @GetMapping(value = "/persons")
    public List<Person> findAllPersons(){
        return personService.getPersons();
    }
    @GetMapping(value = "/")
    public String index(){
        return "Hello world!";
    }

    @PostMapping(value = "/persons")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        personService.addPerson(person);
        if (Objects.isNull(person)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}")
                .buildAndExpand(person.getFirstName())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
