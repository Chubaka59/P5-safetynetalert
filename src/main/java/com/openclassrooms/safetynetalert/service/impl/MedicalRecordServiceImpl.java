package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.impl.MedicalRecordRepositoryImpl;
import com.openclassrooms.safetynetalert.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepositoryImpl medicalRecordRepository;

    @Override
    public List<MedicalRecord> getMedicalRecords(){
        return medicalRecordRepository.getAllMedicalRecords();
    }

    @Override
    public boolean add(MedicalRecord medicalRecord) {
        return medicalRecordRepository.add(medicalRecord);
    }

    @Override
    public boolean delete(String firstName, String lastName) {
        return medicalRecordRepository.delete(firstName, lastName);
    }

    @Override
    public boolean update(MedicalRecord medicalRecord, String firstName, String lastName) {
        return medicalRecordRepository.update(medicalRecord, firstName, lastName);
    }
}
