package com.openclassrooms.safetynetalert.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalert.model.Data;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.Person;
import com.openclassrooms.safetynetalert.repository.DataRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DataRepositoryImpl implements DataRepository {

    private Data data;

    private final ObjectMapper objectMapper;


    @PostConstruct
    public void getDataFromFile() throws IOException  {
        File resourceFile = new ClassPathResource("data.json").getFile();
        log.info("File address = " + resourceFile.getAbsolutePath());
        data = objectMapper.readValue( resourceFile , Data.class);
    }

    public List<Person> getPersons(){
        return data.getPersons();
    }

    public List<FireStation> getFireStations(){
        return data.getFireStations();
    }

    public List<MedicalRecord> getMedicalRecords(){
        return data.getMedicalRecords();
    }
}
