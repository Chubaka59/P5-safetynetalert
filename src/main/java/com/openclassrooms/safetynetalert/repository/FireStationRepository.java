package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FireStationRepository {
    public static List<FireStation> fireStationList;

    public List<FireStation> getAllFireStation(){

        return fireStationList;
    }
}
