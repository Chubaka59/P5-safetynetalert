package com.openclassrooms.safetynetalert.exception.medicalrecord;

public class MedicalRecordNotFoundException extends RuntimeException {
    public MedicalRecordNotFoundException(String firstName, String lastName) {
        super("medicalRecord not found with firstName = " + firstName + " and lastName = " + lastName);
    }
}
