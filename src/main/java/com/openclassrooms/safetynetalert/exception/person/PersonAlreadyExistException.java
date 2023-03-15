package com.openclassrooms.safetynetalert.exception.person;

public class PersonAlreadyExistException extends RuntimeException {
    public PersonAlreadyExistException(String firstName, String lastName){
        super("This person with firstName = " + firstName + " and lastName = " + lastName + " already exist!");
    }
}
