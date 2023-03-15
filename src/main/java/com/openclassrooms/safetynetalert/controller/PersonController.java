package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.dto.person.CreatePersonDTO;
import com.openclassrooms.safetynetalert.dto.person.UpdatePersonDTO;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.service.PersonService;
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
    private final PersonService personService;
    @GetMapping (value = "/person")
    public List<Person> findAllPersons(){
        return personService.getPersons();
    }

    @PostMapping (value = "/person")
    public ResponseEntity<Person> add(@RequestBody @Validated CreatePersonDTO personDTO) {
        try {
            personService.add(personDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping (value = "/person/{firstName}&{lastName}")
    public ResponseEntity<Person> delete(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            personService.delete(firstName, lastName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping (value = "/person/{firstName}&{lastName}")
    public ResponseEntity<Person> update(@RequestBody @Validated UpdatePersonDTO personDTO, @PathVariable String firstName, @PathVariable String lastName){
        try {
            personService.update(personDTO, firstName, lastName);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
