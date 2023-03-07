package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {
    FireStationRepository fireStationRepository = new FireStationRepository();

    public List<FireStation> getFireStations(){
        return fireStationRepository.getAllFireStation();
    }
}
