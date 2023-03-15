package com.openclassrooms.safetynetalert.dto.medicalrecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateMedicalRecordDTO {

    @NotNull
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthdate;

    @NotNull
    private List<String> medications;

    @NotNull
    private List<String> allergies;
}
