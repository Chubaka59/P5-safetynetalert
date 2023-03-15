package com.openclassrooms.safetynetalert.service;

import com.openclassrooms.safetynetalert.dto.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;

public interface FireStationService {


    List<FireStation> getFireStations();


    FireStation add(CreateFireStationDTO fireStationDTO);



    FireStation delete(String address);


    FireStation update (UpdateFireStationDTO fireStationDTO, String address);


    List<FireStationDTO> getPersonsFromFireStation(int stationNumber);
}
