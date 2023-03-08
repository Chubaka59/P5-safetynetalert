package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MedicalRecord {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String birthdate;
    @NotEmpty
    private String[] medications;
    @NotEmpty
    private String[] allergies;
}
