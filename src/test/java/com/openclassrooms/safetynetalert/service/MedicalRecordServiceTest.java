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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MedicalRecordServiceTest {
    private MedicalRecordRepositoryImpl medicalRecordRepository = mock(MedicalRecordRepositoryImpl.class);
    @MockBean
    private MedicalRecordServiceImpl medicalRecordService;


    @BeforeEach
    public void setupPerTest(){
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
        when(medicalRecordRepository.getMedicalRecord(anyString(),anyString())).thenReturn(Optional.empty());

        //WHEN we would add the medicalRecord
        medicalRecordService.add(new CreateMedicalRecordDTO());

        //THEN medicalRecordRepository.add is called 1 time
        verify(medicalRecordRepository, times(1)).add(any(CreateMedicalRecordDTO.class));
    }

    @Test
    public void addWhenMedicalRecordAlreadyExistTest(){
        //GIVEN the medicalRecord we would add already exist
        CreateMedicalRecordDTO addExistingMedicalRecord = new CreateMedicalRecordDTO("test", "test", LocalDate.now(), null, null);
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);

        when(medicalRecordRepository.getMedicalRecord(addExistingMedicalRecord.getFirstName(), addExistingMedicalRecord.getLastName())).thenReturn(Optional.of(existingMedicalRecord));

        //WHEN we would add the medicalRecord THEN an exception is raised
        assertThrows(MedicalRecordAlreadyExistException.class, () -> medicalRecordService.add(addExistingMedicalRecord));
    }

    @Test
    public void deleteTest(){
        //GIVEN the medicalRecord we would delete is found
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(new MedicalRecord()));

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
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        UpdateMedicalRecordDTO updateMedicalRecordDTO = new UpdateMedicalRecordDTO();
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(existingMedicalRecord));

        //WHEN we would update
        medicalRecordService.update(updateMedicalRecordDTO, anyString(), anyString());

        //THEN medicalRecordRepository.update is called 1 time
        verify(medicalRecordRepository, times(1)).update(existingMedicalRecord, updateMedicalRecordDTO);
    }

    @Test
    public void updateWhenMedicalRecordIsNotFoundTest(){
        //GIVEN the medicalRecord we would update is not found
        when(medicalRecordRepository.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.empty());
        UpdateMedicalRecordDTO updateMedicalRecord = new UpdateMedicalRecordDTO();

        //WHEN we would update the medicalRecord THEN an exception is raised
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.update(updateMedicalRecord, anyString(), anyString()));
    }
}
