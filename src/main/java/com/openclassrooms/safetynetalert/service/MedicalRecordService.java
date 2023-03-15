package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {


    List<MedicalRecord> getMedicalRecords();


    MedicalRecord add(CreateMedicalRecordDTO medicalRecordDTO);


    MedicalRecord delete(String firstName, String lastName);


    MedicalRecord update(UpdateMedicalRecordDTO medicalRecordDTO, String firstName, String lastName);
}
