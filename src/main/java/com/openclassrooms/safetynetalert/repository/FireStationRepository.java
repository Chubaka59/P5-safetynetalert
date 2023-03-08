package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;

public interface FireStationRepository {
    /**
     * get all FireStation from DataRepository
     * @return a list of FireStation
     */
    List<FireStation> getAllFireStation();
}
