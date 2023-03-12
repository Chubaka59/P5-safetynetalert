package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    /**
     * get medicalRecordList from repository
     * @return a list of medicalRecord
     */
    List<MedicalRecord> getMedicalRecords();

    /**
     * add a medicalRecord to ther medicalRecordList
     * @param medicalRecord the medicalRecord to add
     * @return a boolean
     */
    boolean add(MedicalRecord medicalRecord);

    /**
     * delete a medicalRecord from the medicalRecordList
     * @param firstName the firstName of the medicalRecord to delete
     * @param lastName the lastName of the medicalRecord to delete
     * @return a boolean
     */
    boolean delete(String firstName, String lastName);

    /**
     * update the information of a medicalRecord in the medicalRecordList
     * @param medicalRecord the medicalRecord to update
     * @param firstName the firstName of the medicalRecord to update
     * @param lastName the lastName of the medicalRecord to update
     * @return a boolean
     */
    boolean update(MedicalRecord medicalRecord, String firstName, String lastName);
}
