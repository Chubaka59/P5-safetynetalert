package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordRepository {
    /**
     * get all MedicalRecord from DataRepository
     * @return a list of MedicalRecords
     */
    List<MedicalRecord> getAllMedicalRecords();

    /**
     * add a medicalRecord to the medicalRecordList
     * @param medicalRecord the medicalRecord to add
     * @return a boolean
     */
    boolean add (MedicalRecord medicalRecord);

    /**
     * delete a medicalRecord from the medicalRecordList
     * @param firstName the firstName of the medicalRecord to delete
     * @param lastName the lastName of the medicalRecord to delete
     * @return a boolean
     */
    boolean delete(String firstName, String lastName);

    /**
     * update the inforamtion of a medicalRecord in the medicalRecordList
     * @param medicalRecord the information to update
     * @param firstName the firstName of the medicalRecord to update
     * @param lastName the lastName of the medicalRecord to update
     * @return a boolean
     */
    boolean update(MedicalRecord medicalRecord, String firstName, String lastName);

    /**
     * check if a medicalRecord is already in the medicalRecordList
     * @param medicalRecord the medicalRecord to check if duplicated
     * @return a boolean
     */
    boolean isDuplicated(MedicalRecord medicalRecord);

    LocalDate getBirthdateListFromPersonList(Person person);
    int getAge(LocalDate birthdate);


}
