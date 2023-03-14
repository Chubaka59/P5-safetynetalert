package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Repository
@RequiredArgsConstructor
public class FireStationRepositoryImpl implements FireStationRepository {

    private final DataRepositoryImpl dataRepository;


    @Override
    public List<FireStation> getAllFireStation(){
        return dataRepository.getFireStations();
    }

    @Override
    public boolean add(FireStation fireStation) {
        if (!isDuplicated(fireStation))
            return dataRepository.getFireStations().add(fireStation);
        return false;
    }

    @Override
    public boolean delete(String address) {
        return dataRepository.getFireStations().removeIf(f -> f.getAddress().equals(address));
    }

    @Override
    public boolean update(FireStation fireStation, String address) {
        AtomicBoolean isDone = new AtomicBoolean(false);
       dataRepository.getFireStations()
                .stream()
                .filter(i -> i.getAddress().equals(address))
                .findFirst()
                .ifPresent(f -> {
                    f.setStation(fireStation.getStation());
                    isDone.set(true);
                });
           return isDone.get();
    }

    @Override
    public boolean isDuplicated(FireStation fireStation) {
        return dataRepository.getFireStations()
                .stream().anyMatch(f -> f.getAddress().equals(fireStation.getAddress()));
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
