package com.openclassrooms.safetynetalert.model;

import java.util.List;

@lombok.Data
public class Data {

    List<Person> persons;
    List<FireStation> firestations;
    List<MedicalRecord> medicalRecords;
}
