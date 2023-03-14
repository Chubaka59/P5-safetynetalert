package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;

public interface FireStationRepository {
    /**
     * get all FireStation from DataRepository
     * @return a list of FireStation
     */
    List<FireStation> getAllFireStation();

    /**
     * add a firestation in the fireStationList
     * @param fireStation the fireStation to add
     * @return a boolean
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

    /**
     * check if a fireStation is already in the fireStationList
     * @param fireStation the fireStation to check if duplicated
     * @return a boolean
     */
    boolean isDuplicated(FireStation fireStation);

    List<String> getAddressFromStationNumber(int stationNumber);
}
