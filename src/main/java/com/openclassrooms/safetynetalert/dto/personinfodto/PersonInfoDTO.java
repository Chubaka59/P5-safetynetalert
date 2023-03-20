package com.openclassrooms.safetynetalert.dto.personinfodto;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class PersonInfoDTO {
    String firstName;
    String lastName;
    String address;
    String mail;
    Integer age;
    List<String> medications;
    List<String> allergies;

    public PersonInfoDTO(Person person, MedicalRecord medicalRecord){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.mail = person.getEmail();
        this.age = medicalRecord.getAge();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
