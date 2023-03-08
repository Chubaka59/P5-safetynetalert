package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {
    public static List<MedicalRecord> medicalRecordList;

    public List<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordList;
    }
}
