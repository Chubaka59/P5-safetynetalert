package com.openclassrooms.safetynetalert.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicalRecordTest {


    @ParameterizedTest
    @CsvSource({"1,true", "18,true", "19,false" , "99,false"})
    void getAge(int age, boolean expectedStatus) {
        // GIVEN a parametrized birthdate
        final var birthdate = LocalDate.now().minusYears(age);

        final var medicalRecord = new MedicalRecord(null, null, birthdate, null, null);

        assertEquals(age, medicalRecord.getAge());
        assertEquals(expectedStatus, medicalRecord.isMinor());
    }
}