package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MedicalRecordRepositoryTest {
    private DataRepository dataRepository = mock(DataRepository.class);
    private MedicalRecordRepository medicalRecordRepository;
    private MedicalRecord medicalRecord;
    private CreateMedicalRecordDTO createMedicalRecordDTO;
    private UpdateMedicalRecordDTO updateMedicalRecordDTO;
    private List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void setupPerTest() {
        medicalRecord = new MedicalRecord();
        createMedicalRecordDTO = new CreateMedicalRecordDTO();
        updateMedicalRecordDTO = new UpdateMedicalRecordDTO();
        medicalRecordRepository = new MedicalRecordRepositoryImpl(dataRepository);
        medicalRecordList = new ArrayList<>();
    }

    @Test
    public void getAllMedicalRecordTest() {
        //GIVEN a list should be returned
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN the method is called
        medicalRecordRepository.getAllMedicalRecords();

        //THEN dataRepository.getMedicalRecord is called 1 time
        verify(dataRepository, times(1)).getMedicalRecords();
    }

    @Test
    public void getMedicalRecordTest(){
        //GIVEN a medicalRecord is in the list
        medicalRecord.setFirstName("test");
        medicalRecord.setLastName("test");
        medicalRecordList.add(medicalRecord);
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN we try ot find this person
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.getMedicalRecord("test", "test");

        //THEN this person is returned as Optional
        verify(dataRepository, times(1)).getMedicalRecords();
        assertEquals(optionalMedicalRecord.get(), medicalRecord);
    }

    @Test
    public void addTest(){
        //GIVEN there is a medicalRecord to add
        createMedicalRecordDTO.setFirstName("test");
        createMedicalRecordDTO.setLastName("test");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN we add the medicalRecord
        medicalRecord = medicalRecordRepository.add(createMedicalRecordDTO);

        //THEN the medicalRecord has been added to the list
        assertEquals(medicalRecordList.get(0), medicalRecord);
    }

    @Test
    public void deleteTest(){
        //GIVEN there is a medicalRecord to delete in the personList
        medicalRecord.setFirstName("test");
        medicalRecord.setLastName("test");
        medicalRecordList.add(medicalRecord);
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN we delete the medicalRecord
        medicalRecord = medicalRecordRepository.delete(medicalRecord);

        //THEN the medicalRecord isn't the list anymore
        assertEquals(medicalRecordList.size(), 0);
    }

    @Test
    public void updateTest(){
        //GIVEN there is a medicalRecord to update in the medicalRecordList and update information
        medicalRecord.setFirstName("test");
        medicalRecord.setLastName("test");
        medicalRecordList.add(medicalRecord);

        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);

        updateMedicalRecordDTO.setBirthdate(LocalDate.now());
        updateMedicalRecordDTO.setMedications(List.of("test"));
        updateMedicalRecordDTO.setAllergies(List.of("test"));

        //WHEN the medicalRecord is updated
        medicalRecord = medicalRecordRepository.update(medicalRecord, updateMedicalRecordDTO);

        //THEN the information of the medicalRecord are updated
        assertEquals(medicalRecordList.get(0), medicalRecord);
    }
}
