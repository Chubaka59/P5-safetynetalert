package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.exception.medicalrecord.MedicalRecordAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.medicalrecord.MedicalRecordNotFoundException;
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
    public MedicalRecord add(CreateMedicalRecordDTO medicalRecordDTO) {
        if (medicalRecordRepository.getMedicalRecord(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName()).isPresent())
            throw new MedicalRecordAlreadyExistException(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        return medicalRecordRepository.add(medicalRecordDTO);
    }

    @Override
    public MedicalRecord delete(String firstName, String lastName) {
        MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecord(firstName, lastName)
                .orElseThrow(() -> new MedicalRecordNotFoundException(firstName, lastName));
        return medicalRecordRepository.delete(medicalRecord);
    }

    @Override
    public MedicalRecord update(UpdateMedicalRecordDTO medicalRecordDTO, String firstName, String lastName) {
        MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecord(firstName, lastName)
                .orElseThrow(() -> new MedicalRecordNotFoundException(firstName, lastName));
        return medicalRecordRepository.update(medicalRecord, medicalRecordDTO);
    }
}
