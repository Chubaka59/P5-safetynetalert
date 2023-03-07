package com.openclassrooms.safetynetalert.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalert.model.Data;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;

@Slf4j
@Repository
public class DataRepository {
    private static final Logger logger = LoggerFactory.getLogger(DataRepository.class);
    String filePath = "src/main/resources/data.json";
    Data data;

    public void getDataFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (filePath.isEmpty() || filePath.isBlank()){
                throw new NullPointerException();
            }
            data = objectMapper.readValue(new File(filePath), Data.class);
        } catch (Exception e) {
            logger.error("Unable to load file", e);
        }
    }

    public Person[] getPersonsFromFile(){
        getDataFromFile(filePath);
        logger.info("Person loaded");
        return data.getPersons();
    }

    public FireStation[] getFireStationsFromFile(){
        getDataFromFile(filePath);
        logger.info("FireStation loaded");
        return data.getFirestations();
    }

    public MedicalRecord[] getMedicalRecordsFromFile(){
        getDataFromFile(filePath);
        logger.info("MedicalRecord loaded");
        return data.getMedicalRecords();
    }
}
