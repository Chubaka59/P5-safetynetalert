package com.openclassrooms.safetynetalert.repository;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationRepository {

    List<FireStation> getAllFireStations();

    public Optional<FireStation> getFireStation(String address);

    FireStation add(CreateFireStationDTO fireStationDTO);


    FireStation delete(FireStation fireStation);


    FireStation update (FireStation fireStation, UpdateFireStationDTO fireStationDTO);

    List<String> getAddressFromStationNumber(int stationNumber);
}
