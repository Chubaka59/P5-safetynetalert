package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.dto.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationAlreadyExistException;
import com.openclassrooms.safetynetalert.exception.firestation.FireStationNotFoundException;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import com.openclassrooms.safetynetalert.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FireStationServiceImpl  implements FireStationService {

    private final FireStationRepository fireStationRepository;

    @Override
    public List<FireStation> getFireStations(){
        return fireStationRepository.getAllFireStations();
    }

    @Override
    public FireStation add(CreateFireStationDTO fireStationDTO) {
        if(fireStationRepository.getFireStation(fireStationDTO.getAddress()).isPresent())
            throw new FireStationAlreadyExistException(fireStationDTO.getAddress());
        return fireStationRepository.add(fireStationDTO);
    }

    @Override
    public FireStation delete(String address) {
        FireStation fireStation = fireStationRepository.getFireStation(address)
                .orElseThrow(() -> new FireStationNotFoundException(address));
        return fireStationRepository.delete(fireStation);
    }

    @Override
    public FireStation update(UpdateFireStationDTO fireStationDTO, String address) {
        FireStation fireStation = fireStationRepository.getFireStation(address)
                .orElseThrow(() -> new FireStationNotFoundException(address));
        return fireStationRepository.update(fireStation, fireStationDTO);
    }

    @Override
    public List<FireStationDTO> getPersonsFromFireStation(int stationNumber) {
        return null;
    }
}
