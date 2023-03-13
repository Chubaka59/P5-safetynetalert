package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    @Null(groups = OnUpdate.class)
    @NotBlank
    private String firstName;
    @Null(groups = OnUpdate.class)
    @NotBlank
    private String lastName;
    @NotBlank(groups = OnUpdate.class)
    @NotBlank
    private String birthdate;
    @NotNull(groups = OnUpdate.class)
    @NotNull
    private List<String> medications;
    @NotNull(groups = OnUpdate.class)
    @NotNull
    private List<String> allergies;
}
