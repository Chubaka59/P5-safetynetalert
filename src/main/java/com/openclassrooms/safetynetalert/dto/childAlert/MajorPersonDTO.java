package com.openclassrooms.safetynetalert.dto.childAlert;

import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

@Data
public class MajorPersonDTO {
    String firstName;
    String lastName;

    public MajorPersonDTO(Person person){
        firstName = person.getFirstName();
        lastName = person.getLastName();
    }
}
