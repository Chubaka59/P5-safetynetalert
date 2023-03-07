package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {
    private DataRepository dataRepository = new DataRepository();

    List<MedicalRecord> medicalRecordList;

    public List<MedicalRecord> getAllMedicalRecords(){
        medicalRecordList = List.of(dataRepository.getMedicalRecordsFromFile());
        return medicalRecordList;
    }
}
