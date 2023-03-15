package com.openclassrooms.safetynetalert.exception.person;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String firstName, String lastName) {
        super("Person not found with firstName = " + firstName +" and lastName = " + lastName );
    }
}
