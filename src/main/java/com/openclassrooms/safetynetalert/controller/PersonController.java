package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.OnUpdate;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.service.impl.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonServiceImpl personService;
    @GetMapping (value = "/person")
    public List<Person> findAllPersons(){
        return personService.getPersons();
    }

    @PostMapping (value = "/person")
    public ResponseEntity<Person> add(@RequestBody @Validated Person person) {
        if (personService.add(person)) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.error("Person already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping (value = "/person/{firstName}&{lastName}")
    public ResponseEntity<Person> delete(@PathVariable String firstName, @PathVariable String lastName){
        if (personService.delete(firstName, lastName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping (value = "/person/{firstName}&{lastName}")
    public ResponseEntity<Person> update(@RequestBody @Validated(OnUpdate.class) Person person, @PathVariable String firstName, @PathVariable String lastName){
        if (personService.update(person, firstName, lastName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
