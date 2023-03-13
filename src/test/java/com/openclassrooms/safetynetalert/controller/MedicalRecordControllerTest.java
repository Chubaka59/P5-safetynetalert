package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.service.impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    @Autowired
    MockMvc mockMvc;
    MedicalRecordServiceImpl medicalRecordService = mock(MedicalRecordServiceImpl.class);
    MedicalRecordController medicalRecordController;
    MedicalRecord medicalRecord;

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
        given(medicalRecordService.add(any(MedicalRecord.class))).willReturn(true);

        //WHEN we post a new medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.add(medicalRecord);

        //THEN the medicalRecord is created
        then(medicalRecordService).should().add(any(MedicalRecord.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addWhenAFieldIsMissing() throws Exception{
        //TODO this is an IT, need to move it in IT Test
        //GIVEN a field is missing in the body
        String body = "{ \"lastName\":\"Test\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }";
        when(medicalRecordService.add(any(MedicalRecord.class))).thenReturn(true);

        //WHEN we post a new medicalRecord
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/medicalrecords")
                .accept(MediaType.APPLICATION_JSON).content(body)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        //THEN we get a response BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void addWhenPersonAlreadyExistTest(){
        //GIVEN the medicalRecord to add already exist
        given(medicalRecordService.add(any(MedicalRecord.class))).willReturn(false);

        //WHEN we add a medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.add(medicalRecord);

        //THEN the response status is CONFLICT
        then(medicalRecordService).should().add(medicalRecord);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void deleteTest(){
        //GIVEN a medicalRecord needs to be deleted
        given(medicalRecordService.delete(anyString(), anyString())).willReturn(true);

        //WHEN we request to delete the person
        ResponseEntity<MedicalRecord> response = medicalRecordController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is OK
        then(medicalRecordService).should().delete(anyString(), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteNotExistingMedicalRecordTest(){
        //GIVEN a medicalRecord that doesn't exist will be deleted
        given(medicalRecordService.delete(anyString(), anyString())).willReturn(false);

        //WHEN we request to delete the medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.delete(anyString(), anyString());

        //THEN the method to delete is called and the response is NOT_FOUND
        then(medicalRecordService).should().delete(anyString(), anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTest(){
        //GIVEN a medicalRecord will be updated
        given(medicalRecordService.update(any(MedicalRecord.class), anyString(), anyString())).willReturn(true);

        //WHEN we update the medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.update(medicalRecord, "test", "test");

        //THEN the method to update is called and the response is OK
        then(medicalRecordService).should().update(any(MedicalRecord.class), anyString(), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateNotExistingMedicalRecordTest(){
        //GIVEN the medicalRecord we would update doesn't exist
        given(medicalRecordService.update(any(MedicalRecord.class), anyString(), anyString())).willReturn(false);

        //WHEN we update the medicalRecord
        ResponseEntity<MedicalRecord> response = medicalRecordController.update(medicalRecord, "test", "test");

        //THEN the response is NOT_FOUND
        then(medicalRecordService).should().update(any(MedicalRecord.class), anyString(), anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
