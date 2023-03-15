package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository {

    List<MedicalRecord> getAllMedicalRecords();

    Optional<MedicalRecord> getMedicalRecord(String firstname, String lastname);

    MedicalRecord add (CreateMedicalRecordDTO medicalRecordDTO);


    MedicalRecord delete(MedicalRecord medicalRecord);


    MedicalRecord update(MedicalRecord medicalRecord, UpdateMedicalRecordDTO medicalRecordDTO);

    LocalDate getBirthdateListFromPersonList(Person person);
    int getAge(LocalDate birthdate);
}
