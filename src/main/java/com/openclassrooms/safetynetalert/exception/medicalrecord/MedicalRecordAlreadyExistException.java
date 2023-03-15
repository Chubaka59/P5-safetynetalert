package com.openclassrooms.safetynetalert.exception.medicalrecord;

public class MedicalRecordAlreadyExistException extends RuntimeException {
    public MedicalRecordAlreadyExistException(String firstName, String lastName){
        super("This medicalRecord with firstName = " + firstName + " and lastName = " + lastName + " already exist!");
    }
}
