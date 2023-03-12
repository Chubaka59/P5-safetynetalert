package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;

public interface FireStationService {

    /**
     * Get all firestations
     * @return a list of FireStation object
     */
    List<FireStation> getFireStations();

    /**
     * add a fireStation to fireStationList
     * @param fireStation
     * @return
     */
    boolean add(FireStation fireStation);


    /**
     * delete an address from the fireStationList
     * @param address the address to delete
     * @return a boolean
     */
    boolean delete(String address);

    /**
     * update the fireStation number of an address
     * @param fireStation the information to update
     * @param address the address of to update
     * @return a boolean
     */
    boolean update (FireStation fireStation, String address);
}
