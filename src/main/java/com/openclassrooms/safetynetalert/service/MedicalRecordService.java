package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();

    public List<MedicalRecord> getMedicalRecords(){
        return medicalRecordRepository.getAllMedicalRecords();
    }
}
