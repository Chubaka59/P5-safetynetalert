package com.openclassrooms.safetynetalert.dto.childAlert;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

@Data
public class MinorPersonDTO {
    String firstName;
    String lastName;
    Integer age;

    public MinorPersonDTO(Person person, MedicalRecord medicalRecord){
        firstName = person.getFirstName();
        lastName = person.getLastName();
        age = medicalRecord.getAge();
    }
}
