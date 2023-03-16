package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.exception.medicalrecord.MedicalRecordAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.medicalrecord.MedicalRecordNotFoundException;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@SpringBootTest
public class MedicalRecordControllerTest {
    private MedicalRecordService medicalRecordService = mock(MedicalRecordService.class);
    private MedicalRecordController medicalRecordController;
    private MedicalRecord medicalRecord;
    private CreateMedicalRecordDTO createMedicalRecordDTO;
    private UpdateMedicalRecordDTO updateMedicalRecordDTO;

    @BeforeEach
    public void setupPerTest(){
        medicalRecord = new MedicalRecord();
        medicalRecordController = new MedicalRecordController(medicalRecordService);
    }

    @Test
    public void findAllMedicalRecordsTest(){
        //WHEN the request to find all medicalrecords is sent
        medicalRecordController.findAllMedicalRecords();

        //THEN medicalRecordService.getMedicalRecord is called
        verify(medicalRecordService, times(1)).getMedicalRecords();
    }

    @Test
    public void addTest(){
        //GIVEN we need to add a medicalRecord
        given(medicalRecordService.add(any(CreateMedicalRecordDTO.class))).willReturn(any(MedicalRecord.class));

        //WHEN we post a new medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.add(createMedicalRecordDTO);

        //THEN the medicalRecord is created
        then(medicalRecordService).should().add(createMedicalRecordDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addWhenPersonAlreadyExistTest(){
        //GIVEN the medicalRecord to add already exist
        given(medicalRecordService.add(any(CreateMedicalRecordDTO.class))).willThrow(new MedicalRecordAlreadyExistException(anyString(), anyString()));

        //WHEN we add a medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.add(createMedicalRecordDTO);

        //THEN the response status is CONFLICT
        then(medicalRecordService).shouldHaveNoInteractions();
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteTest(){
        //GIVEN a medicalRecord needs to be deleted
        given(medicalRecordService.delete(anyString(), anyString())).willReturn(medicalRecord);

        //WHEN we request to delete the person
        ResponseEntity<MedicalRecord> response = medicalRecordController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is OK
        then(medicalRecordService).should().delete(anyString(), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteNotExistingMedicalRecordTest(){
        //GIVEN a medicalRecord that doesn't exist will be deleted
        given(medicalRecordService.delete(anyString(), anyString())).willThrow(new MedicalRecordNotFoundException(anyString(), anyString()));

        //WHEN we request to delete the medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is NOT_FOUND
        then(medicalRecordService).shouldHaveNoInteractions();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTest(){
        //GIVEN a medicalRecord will be updated
        given(medicalRecordService.update(updateMedicalRecordDTO, "test", "test")).willReturn(medicalRecord);

        //WHEN we update the medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.update(updateMedicalRecordDTO, "test", "test");

        //THEN the method to update is called and the response is OK
        then(medicalRecordService).should().update(updateMedicalRecordDTO, "test", "test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateNotExistingMedicalRecordTest(){
        //GIVEN the medicalRecord we would update doesn't exist
        given(medicalRecordService.update(any(UpdateMedicalRecordDTO.class), anyString(), anyString())).willThrow(new MedicalRecordNotFoundException(anyString(), anyString()));

        //WHEN we update the medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.update(any(UpdateMedicalRecordDTO.class), anyString(), anyString());

        //THEN the response is NOT_FOUND
        then(medicalRecordService).shouldHaveNoInteractions();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
