package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.DataRepository;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FireStationRepositoryImpl implements FireStationRepository {

    private final DataRepository dataRepository;


    @Override
    public List<FireStation> getAllFireStations() {
        return dataRepository.getFireStations();
    }

    @Override
    public Optional<FireStation> getFireStation(String address) {
        return dataRepository.getFireStations()
                .stream()
                .filter(f -> f.getAddress().equals(address))
                .findFirst();
    }

    @Override
    public FireStation add(CreateFireStationDTO fireStationDTO) {
        dataRepository.getFireStations().add(new FireStation(fireStationDTO));
        return getFireStation(fireStationDTO.getAddress()).orElseThrow();
    }

    @Override
    public FireStation delete(FireStation fireStation) {
        dataRepository.getFireStations().removeIf(f -> f.getAddress().equals(fireStation.getAddress()));
        return fireStation;
    }

    @Override
    public FireStation update(FireStation fireStation, UpdateFireStationDTO fireStationDTO) {
        dataRepository.getFireStations()
                .stream()
                .filter(f -> f.getAddress().equals(fireStation.getAddress()))
                .findFirst()
                .ifPresent(f -> f.update(fireStationDTO));
        return getFireStation(fireStation.getAddress()).orElseThrow();
    }

    @Override
    public List<String> getAddressFromStationNumber(int stationNumber) {
        return dataRepository.getFireStations()
                .stream()
                .filter(f -> f.getStation() == stationNumber)
                .map(f -> f.getAddress())
                .toList();
    }
}
