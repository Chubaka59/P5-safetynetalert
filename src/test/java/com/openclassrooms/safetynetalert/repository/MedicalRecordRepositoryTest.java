package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MedicalRecordRepositoryTest {
    private DataRepository dataRepository = mock(DataRepository.class);
    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    public void setupPerTest() {
        medicalRecordRepository = new MedicalRecordRepositoryImpl(dataRepository);
    }

    @Test
    public void getAllMedicalRecordTest() {
        //GIVEN a list should be returned
        MedicalRecord existingMedicalRecord = new MedicalRecord();
        when(dataRepository.getMedicalRecords()).thenReturn(List.of(existingMedicalRecord));

        //WHEN the method is called
        medicalRecordRepository.getAllMedicalRecords();

        //THEN dataRepository.getMedicalRecord is called 1 time
        verify(dataRepository, times(1)).getMedicalRecords();
    }

    @Test
    public void getMedicalRecordTest(){
        //GIVEN a medicalRecord is in the list
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        when(dataRepository.getMedicalRecords()).thenReturn(List.of(existingMedicalRecord));

        //WHEN we try ot find this person
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.getMedicalRecord("test", "test");

        //THEN this person is returned as Optional
        verify(dataRepository, times(1)).getMedicalRecords();
        assertEquals(Optional.of(existingMedicalRecord), optionalMedicalRecord);
    }

    @Test
    public void addTest(){
        //GIVEN there is a medicalRecord to add
        List<MedicalRecord> MedicalRecordList = new ArrayList<>();
        when(dataRepository.getMedicalRecords()).thenReturn(MedicalRecordList);

        //WHEN we add the medicalRecord
        MedicalRecord medicalRecord = medicalRecordRepository.add(new CreateMedicalRecordDTO("test", "test", LocalDate.now(), null, null));

        //THEN the medicalRecord has been added to the list
        assertEquals(MedicalRecordList.get(0), medicalRecord);
    }

    @Test
    public void deleteTest(){
        //GIVEN there is a medicalRecord to delete in the personList
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        medicalRecordList.add(existingMedicalRecord);
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN we delete the medicalRecord
        MedicalRecord deletedMedicalRecord = medicalRecordRepository.delete(existingMedicalRecord);

        //THEN the medicalRecord isn't the list anymore
        assertEquals(0, medicalRecordList.size());
        assertEquals(existingMedicalRecord, deletedMedicalRecord);
    }

    @Test
    public void updateTest(){
        //GIVEN there is a medicalRecord to update in the medicalRecordList and update information
        MedicalRecord existingMedicalRecord = new MedicalRecord("test", "test", LocalDate.now(), null, null);
        UpdateMedicalRecordDTO updateMedicalRecordDTO = new UpdateMedicalRecordDTO(LocalDate.now().minusYears(10), null, null);
        when(dataRepository.getMedicalRecords()).thenReturn(List.of(existingMedicalRecord));

        MedicalRecord expectedMedicalRecord = new MedicalRecord("test", "test", LocalDate.now().minusYears(20), null, null);

        //WHEN the medicalRecord is updated
        MedicalRecord medicalRecord = medicalRecordRepository.update(existingMedicalRecord, updateMedicalRecordDTO);

        //THEN the information of the medicalRecord are updated
        assertEquals(existingMedicalRecord, medicalRecord);
    }
}
