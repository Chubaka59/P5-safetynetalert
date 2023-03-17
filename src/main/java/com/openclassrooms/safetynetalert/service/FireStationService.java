package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.phonealert.PhoneAlertDTO;
import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;

public interface FireStationService {
    /**
     * Get a list of all fireStations
     * @return a list of fireStations
     */
    List<FireStation> getFireStations();

    /**
     * Check if the fireStation to add exists then call the method to add it or throw an Exception
     * @param fireStationDTO the information of the fireStation to add
     * @return the fireStation that has been added
     */
    FireStation add(CreateFireStationDTO fireStationDTO);

    /**
     * Check if the fireStation to delete exists then call the method to delete or throw an Exception
     * @param address the address of the fireStation to delete
     * @return the fireStation that has been deleted
     */
    FireStation delete(String address);

    /**
     * Check if the fireStation to update exists then call the method to update or throw an Exception
     * @param fireStationDTO the information of the fireStation to update
     * @param address the address of the fireStation to update
     * @return the fireStation that has been updated
     */
    FireStation update (UpdateFireStationDTO fireStationDTO, String address);

    FireStationDTO getPersonsFromFireStation(int stationNumber);

    List<PhoneAlertDTO> getPhoneAlert(int firestation);
}
