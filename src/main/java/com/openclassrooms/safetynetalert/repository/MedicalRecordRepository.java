package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepository {
    /**
     * get all MedicalRecord from DataRepository
     * @return a list of MedicalRecords
     */
    List<MedicalRecord> getAllMedicalRecords();
}
