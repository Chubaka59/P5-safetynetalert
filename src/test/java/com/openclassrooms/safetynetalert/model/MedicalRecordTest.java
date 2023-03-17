package com.openclassrooms.safetynetalert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MedicalRecordTest {
    private MedicalRecord medicalRecord = mock(MedicalRecord.class);

    @BeforeEach
    public void setupPerTest(){
        medicalRecord = new MedicalRecord();
    }

    @Test
    public void getAgeTest(){
        //GIVEN the birthdate is 01/01/2000
        medicalRecord.setBirthdate(LocalDate.now().minusYears(20));

        //WHEN we calculate the age
        int age = medicalRecord.getAge();

        //THEN the age is calculated
        assertEquals(age, 20);
    }

    @Test
    @Disabled
    public void isMinorTest(){
        //GIVEN the age is 20
        when(medicalRecord.getAge()).thenReturn(20);

        //WHEN the method is called THEN the age is > 18
        assertFalse(medicalRecord.isMinor());
    }
}
