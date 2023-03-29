package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.fire.FireDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.flood.FloodDTO;
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

    /**
     * get a list of person filtered by address from the addresslist of the specified FireStation and count the number of minor persons
     * @param stationNumber the fireStationNumber to get the address
     * @return the fireStationDTO to return
     */
    FireStationDTO getPersonsFromFireStation(int stationNumber);

    /**
     * get a list of phoneNumber filtered by fireStationNumber
     * @param firestation the fireStationNumber to filter
     * @return a list of PhoneAlertDTO
     */
    List<PhoneAlertDTO> getPhoneAlertList(int firestation);

    /**
     * Get the stationNumber of an address and the personList of this address
     * @param address a String of the address
     * @return The FireDTO
     */
    FireDTO getFireStationFromAddress(String address);

    /**
     * Get a list of addresses and the person linked to those address from a list of FireStation
     * @param stations a list of FireStation
     * @return a list of FloodDTO
     */
    List<FloodDTO> getHomeListFromFireStationList(List<Integer> stations);
}
