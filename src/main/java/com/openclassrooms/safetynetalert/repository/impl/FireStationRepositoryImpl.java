package com.openclassrooms.safetynetalert.repository.impl;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FireStationRepositoryImpl implements FireStationRepository {

    private final DataRepositoryImpl dataRepository;


    public List<FireStation> getAllFireStation(){

        return dataRepository.getFireStations();
    }
}
