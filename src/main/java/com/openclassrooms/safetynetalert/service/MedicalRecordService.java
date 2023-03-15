package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    /**
     * Get a List of all medicalRecords
     * @return a list of medicalRecords
     */
    List<MedicalRecord> getMedicalRecords();

    /**
     * Check if the medicalRecord to add exists then call the method to add it or throw an Exception
     * @param medicalRecordDTO the information of the medicalRecord to add
     * @return the medicalRecord that has been added
     */
    MedicalRecord add(CreateMedicalRecordDTO medicalRecordDTO);

    /**
     * Check if the medicalRecord to add exists then call the method to delete or throw an Exception
     * @param firstName the firstName of the medicalRecord to delete
     * @param lastName the lastName of the medicalRecord to delete
     * @return the medicalRecord that has been deleted
     */
    MedicalRecord delete(String firstName, String lastName);

    /**
     * Check if the medicalRecord to update exists then call the method to update or throw an Exception
     * @param medicalRecordDTO the information of the medicalRecord to update
     * @param firstName the firstName of the medicalRecord to update
     * @param lastName the lastName of the medicalRecord to update
     * @return the person that has been updated
     */
    MedicalRecord update(UpdateMedicalRecordDTO medicalRecordDTO, String firstName, String lastName);
}
