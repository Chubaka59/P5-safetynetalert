package com.openclassrooms.safetynetalert.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"firstName","lastName","birthdate","medications","allergies"})
public class MedicalRecord {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("medications")
    private String[] medications;
    @JsonProperty("allergies")
    private String[] allergies;
}
