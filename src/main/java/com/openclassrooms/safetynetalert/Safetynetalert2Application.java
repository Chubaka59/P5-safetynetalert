package com.openclassrooms.safetynetalert;

import com.openclassrooms.safetynetalert.repository.DataRepository;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.repository.MedicalRecordRepository;
import com.openclassrooms.safetynetalert.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Safetynetalert2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Safetynetalert2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //List creation at startup
        DataRepository dataRepository = new DataRepository();

        PersonRepository.personList = new ArrayList<>(List.of(dataRepository.getPersonsFromFile()));
        FireStationRepository.fireStationList = new ArrayList<>(List.of(dataRepository.getFireStationsFromFile()));
        MedicalRecordRepository.medicalRecordList = new ArrayList<>(List.of(dataRepository.getMedicalRecordsFromFile()));
    }
}
