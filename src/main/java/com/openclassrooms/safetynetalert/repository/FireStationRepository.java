package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FireStationRepository {
    private DataRepository dataRepository = new DataRepository();

    List<FireStation> fireStationList;

    public List<FireStation> getAllFireStation(){
        fireStationList = List.of(dataRepository.getFireStationsFromFile());
        return fireStationList;
    }
}
