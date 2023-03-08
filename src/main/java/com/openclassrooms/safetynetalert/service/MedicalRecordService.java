package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    /**
     * get medicalRecordList from repository
     * @return a list of medicalRecord
     */
    List<MedicalRecord> getMedicalRecords();
}
