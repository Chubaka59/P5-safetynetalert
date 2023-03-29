package com.openclassrooms.safetynetalert.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
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

    public MedicalRecord(CreateMedicalRecordDTO medicalRecordDTO){
        this.setFirstName(medicalRecordDTO.getFirstName());
        this.setLastName(medicalRecordDTO.getLastName());
        this.setBirthdate(medicalRecordDTO.getBirthdate());
        this.setMedications(medicalRecordDTO.getMedications());
        this.setAllergies(medicalRecordDTO.getAllergies());
    }

    public MedicalRecord update(UpdateMedicalRecordDTO medicalRecordDTO){
        this.setBirthdate(medicalRecordDTO.getBirthdate());
        this.setMedications(medicalRecordDTO.getMedications());
        this.setAllergies(medicalRecordDTO.getAllergies());
        return this;
    }

    public Integer getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public boolean isMinor(){
        return getAge() <= 18;
    }
}
