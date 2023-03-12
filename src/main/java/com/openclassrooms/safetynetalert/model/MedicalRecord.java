package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class MedicalRecord {
    @Null(groups = OnUpdate.class)
    @NotBlank
    String firstName;
    @Null(groups = OnUpdate.class)
    @NotBlank
    String lastName;
    @NotBlank(groups = OnUpdate.class)
    @NotBlank
    String birthdate;
    String[] medications;
    String[] allergies;
}
