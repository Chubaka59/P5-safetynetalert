package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {
    public static List<MedicalRecord> medicalRecordList;

    public List<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordList;
    }
}
