package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;

import java.io.IOException;
import java.util.List;

public interface DataRepository {
    /**
     * Load file from resource and extract data
     * @throws IOException
     */
    void getDataFromFile() throws IOException;

    /**
     * Extract person list from data
     * @return a list of Person
     */
    List<Person> getPersons();

    /**
     * Extract FireStation from data
     * @return a list of FireStation
     */
    List<FireStation> getFireStations();

    /**
     * Extract MedicalRecord from data
     * @return a list of MedicalRecord
     */
    List<MedicalRecord> getMedicalRecords();
}
