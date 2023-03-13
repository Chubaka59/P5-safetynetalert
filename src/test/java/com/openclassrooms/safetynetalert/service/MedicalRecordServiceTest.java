package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import com.openclassrooms.safetynetalert.service.impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MedicalRecordServiceTest {
    MedicalRecordRepositoryImpl medicalRecordRepository = mock(MedicalRecordRepositoryImpl.class);
    MedicalRecordServiceImpl medicalRecordService;
    MedicalRecord medicalRecord;

    @BeforeEach
    public void setupPerTest(){
        medicalRecord = new MedicalRecord();
        medicalRecordService = new MedicalRecordServiceImpl(medicalRecordRepository);
    }

    @Test
    public void getMedicalRecordsTest(){
        //GIVEN a list should be returned
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN the method is called
        medicalRecordService.getMedicalRecords();

        //THEN medicalRecordRepository.getAllMedicalRecords is called 1 time
        verify(medicalRecordRepository, times(1)).getAllMedicalRecords();
    }

    @Test
    public void addTest(){
        //GIVEN when the method is called, it should return a boolean
        when(medicalRecordRepository.add(any(MedicalRecord.class))).thenReturn(true);

        //WHEN the method is called
        medicalRecordService.add(medicalRecord);

        //THEN medicalRecordRepository.ad is called 1 time
        verify(medicalRecordRepository, times(1)).add(any(MedicalRecord.class));
    }

    @Test
    public void deleteTest(){
        //GIVEN when the method is called, it should return a boolean
        when(medicalRecordRepository.delete(anyString(), anyString())).thenReturn(true);

        //WHEN the method is called
        medicalRecordService.delete(anyString(), anyString());

        //THEN medicalRecordRepository.delete is called 1 time
        verify(medicalRecordRepository, times(1)).delete(anyString(), anyString());
    }

    @Test
    public void updateTest(){
        //GIVEN when the method is called, it should return a boolean
        when(medicalRecordRepository.update(any(MedicalRecord.class), anyString(), anyString())).thenReturn(true);

        //WHEN the method is called
        medicalRecordService.update(medicalRecord, "test", "test");

        //THEN medicalRecordRepository.update is called 1 time
        verify(medicalRecordRepository, times(1)).update(any(MedicalRecord.class), anyString(), anyString());
    }
}
