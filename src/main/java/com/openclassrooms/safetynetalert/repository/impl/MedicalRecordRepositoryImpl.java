package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.repository.DataRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {
    private final DataRepository dataRepository;

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return dataRepository.getMedicalRecords();
    }

    public Optional<MedicalRecord> getMedicalRecord(String firstname, String lastname) {
        return dataRepository.getMedicalRecords()
                .stream()
                .filter(p -> p.getFirstName().equals(firstname))
                .filter(p -> p.getLastName().equals(lastname))
                .findFirst();
    }

    @Override
    public MedicalRecord add(CreateMedicalRecordDTO medicalRecordDTO) {
        dataRepository.getMedicalRecords().add(new MedicalRecord().create(medicalRecordDTO));
        return getMedicalRecord(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName()).orElseThrow();
    }

    @Override
    public MedicalRecord delete(MedicalRecord medicalRecord) {
        dataRepository.getMedicalRecords().removeIf(m -> m.getFirstName().equals(medicalRecord.getFirstName()) && m.getLastName().equals(medicalRecord.getLastName()));
        return medicalRecord;
    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord, UpdateMedicalRecordDTO medicalRecordDTO) {
        dataRepository.getMedicalRecords()
                .stream()
                .filter(m -> m.getFirstName().equals(medicalRecord.getFirstName()) && m.getLastName().equals(medicalRecord.getLastName()))
                .findFirst()
                .ifPresent(m -> m.update(medicalRecordDTO));
        return getMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()).orElseThrow();
    }
}
