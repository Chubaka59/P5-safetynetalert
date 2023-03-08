package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import com.openclassrooms.safetynetalert.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    MedicalRecordRepositoryImpl medicalRecordRepository = new MedicalRecordRepositoryImpl();

    public List<MedicalRecord> getMedicalRecords(){
        return medicalRecordRepository.getAllMedicalRecords();
    }
}
