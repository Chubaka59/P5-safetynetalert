package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.exception.medicalrecord.MedicalRecordAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.medicalrecord.MedicalRecordNotFoundException;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import com.openclassrooms.safetynetalert.service.impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MedicalRecordServiceTest {
    private MedicalRecordRepositoryImpl medicalRecordRepository = mock(MedicalRecordRepositoryImpl.class);
    private MedicalRecordServiceImpl medicalRecordService;
    private MedicalRecord medicalRecord;
    private CreateMedicalRecordDTO createMedicalRecordDTO = mock(CreateMedicalRecordDTO.class);
    private UpdateMedicalRecordDTO updateMedicalRecordDTO;

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
        //GIVEN the medicalRecord we would add doesn't exist
        when(medicalRecordRepository.getMedicalRecord(createMedicalRecordDTO.getFirstName(), createMedicalRecordDTO.getLastName())).thenReturn(Optional.empty());

        //WHEN we would add the medicalRecord
        medicalRecordService.add(createMedicalRecordDTO);

        //THEN medicalRecordRepository.add is called 1 time
        verify(medicalRecordRepository, times(1)).add(any(CreateMedicalRecordDTO.class));
    }

    @Test
    public void addWhenMedicalRecordAlreadyExistTest(){
        //GIVEN the medicalRecord we would add already exist
        when(medicalRecordRepository.getMedicalRecord(createMedicalRecordDTO.getFirstName(), createMedicalRecordDTO.getLastName())).thenReturn(Optional.of(medicalRecord));

        //WHEN we would add the medicalRecord THEN an exception is raised
        assertThrows(MedicalRecordAlreadyExistException.class, () -> medicalRecordService.add(createMedicalRecordDTO));
    }

    @Test
    public void deleteTest(){
        //GIVEN the medicalRecord we would delete is found
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        //WHEN we would delete
        medicalRecordService.delete(anyString(), anyString());

        //THEN medicalRecordRepository.delete is called 1 time
        verify(medicalRecordRepository, times(1)).delete(any(MedicalRecord.class));
    }

    @Test
    public void deleteWhenMedicalRecordIsNotFoundTest(){
        //GIVEN the medicalRecord we would delete is not found
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.empty());

        //WHEN we would delete the medicalRecord THEN an exception is raised
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.delete(anyString(), anyString()));
    }

    @Test
    public void updateTest(){
        //GIVEN the medicalRecord we would update is found
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        //WHEN we would update
        medicalRecordService.update(updateMedicalRecordDTO, anyString(), anyString());

        //THEN medicalRecordRepository.update is called 1 time
        verify(medicalRecordRepository, times(1)).update(medicalRecord, updateMedicalRecordDTO);
    }

    @Test
    public void updateWhenMedicalRecordIsNotFoundTest(){
        //GIVEN the medicalRecord we would update is not found
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.empty());

        //WHEN we would update the medicalRecord THEN an exception is raised
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.update(updateMedicalRecordDTO, anyString(), anyString()));
    }
}
