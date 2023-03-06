package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
