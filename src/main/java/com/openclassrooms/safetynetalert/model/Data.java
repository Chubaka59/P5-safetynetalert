package com.openclassrooms.safetynetalert.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@lombok.Data
@JsonPropertyOrder({"persons","firestations","medicalrecords"})
public class Data {
    @JsonProperty("persons")
    Person[] persons;
    @JsonProperty("firestations")
    FireStation[] firestations;
    @JsonProperty("medicalrecords")
    MedicalRecord[] medicalRecords;
}
