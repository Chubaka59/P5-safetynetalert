package com.openclassrooms.safetynetalert.dto.medicalrecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMedicalRecordDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthdate;

    @NotNull
    private List<String> medications;

    @NotNull
    private List<String> allergies;
}
