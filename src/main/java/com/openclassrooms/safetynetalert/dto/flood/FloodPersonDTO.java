package com.openclassrooms.safetynetalert.dto.flood;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class FloodPersonDTO {
    String firstName;
    String lastName;
    String phone;
    Integer age;
    List<String> medications;
    List<String> allergies;

    public FloodPersonDTO(Person person, MedicalRecord medicalRecord){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = medicalRecord.getAge();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}
