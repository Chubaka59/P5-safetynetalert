package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationRepository {
    /**
     * Get a list of all fireStations
     * @return a list of fireStations
     */
    List<FireStation> getAllFireStations();

    /**
     * Get a fireStation from its address
     * @param address the address of the fireStation
     * @return an Optional of the fireStation
     */
    Optional<FireStation> getFireStation(String address);

    /**
     * Add a fireStation to the fireStationList
     * @param fireStationDTO the fireStation to add
     * @return the fireStation that has been added or throw an Exception
     */
    FireStation add(CreateFireStationDTO fireStationDTO);

    /**
     * Delete a fireStation from the fireStationList
     * @param fireStation the fireStation to delete
     * @return the fireStation that has been deleted
     */
    FireStation delete(FireStation fireStation);

    /**
     * Update the information of a fireStation
     * @param fireStation the fireStation to update
     * @param fireStationDTO the fireStationDTO to get the information to update
     * @return the fireStation that has been updated or throw an Exception
     */
    FireStation update (FireStation fireStation, UpdateFireStationDTO fireStationDTO);

    /**
     * Get the addressList of a fireStation
     * @param stationNumber the fireStation number
     * @return a list of String
     */
    List<String> getAddressFromStationNumber(int stationNumber);
}
