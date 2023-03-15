package com.openclassrooms.safetynetalert.dto;

import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

@Data
public class PersonDTO {
    String firstName;
    String lastName;
    String address;
    String phone;

    public PersonDTO(Person person){
        firstName = person.getFirstName();
        lastName = person.getLastName();
        address = person.getAddress() + " " + person.getZip() + " " + person.getCity();
        phone = person.getPhone();
    }
}
