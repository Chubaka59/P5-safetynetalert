package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.OnUpdate;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.service.impl.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonServiceImpl personService;
    @GetMapping (value = "/persons")
    public List<Person> findAllPersons(){
        return personService.getPersons();
    }

    @PostMapping (value = "/persons")
    public ResponseEntity<Person> add(@RequestBody @Validated Person person) {
        if (personService.add(person)) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(person.getFirstName())
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            logger.error("Person already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping (value = "/persons/{firstName}&{lastName}")
    public ResponseEntity<Person> delete(@PathVariable String firstName, @PathVariable String lastName){
        if (personService.delete(firstName, lastName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping (value = "/persons/{firstName}&{lastName}")
    public ResponseEntity<Person> update(@RequestBody @Validated(OnUpdate.class) Person person, @PathVariable String firstName, @PathVariable String lastName){
        if (personService.update(person, firstName, lastName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
