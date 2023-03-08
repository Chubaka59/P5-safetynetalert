package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;

public interface FireStationService {

    /**
     * Get all firestations
     * @return a list of FireStation object
     */
    List<FireStation> getFireStations();
}
