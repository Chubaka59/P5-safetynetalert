package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository {
    /**
     * Get a list of all medicalRecords
     * @return a list of medicalRecords
     */
    List<MedicalRecord> getAllMedicalRecords();

    /**
     * Get a medicalRecord
     * @param firstname the firstName of the medicalRecord
     * @param lastname the lastName of the medicalRecord
     * @return an Optional of the medicalRecord
     */
    Optional<MedicalRecord> getMedicalRecord(String firstname, String lastname);

    /**
     * Add a medicalRecord to the medicalRecordList
     * @param medicalRecordDTO the medicalRecord to Add
     * @return the medicalRecord that has been added or throw an Exception
     */
    MedicalRecord add (CreateMedicalRecordDTO medicalRecordDTO);

    /**
     * Delete a medicalRecord from the medicalRecordList
     * @param medicalRecord the medicalRecord to delete
     * @return the medicalRecord that has been deleted
     */
    MedicalRecord delete(MedicalRecord medicalRecord);

    /**
     * Update the information of a medicalRecord
     * @param medicalRecord the medicalRecord to update
     * @param medicalRecordDTO the medicalRecordDTO to get the information to update
     * @return the medicalRecord that has been updated or throw an Exception
     */
    MedicalRecord update(MedicalRecord medicalRecord, UpdateMedicalRecordDTO medicalRecordDTO);

    LocalDate getBirthdateListFromPersonList(Person person);
    int getAge(LocalDate birthdate);
}
