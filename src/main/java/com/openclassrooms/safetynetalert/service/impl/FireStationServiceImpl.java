package com.openclassrooms.safetynetalert.service.impl;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.repository.impl.FireStationRepositoryImpl;
import com.openclassrooms.safetynetalert.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FireStationServiceImpl  implements FireStationService {

    private final FireStationRepositoryImpl fireStationRepository;


    @Override
    public List<FireStation> getFireStations(){
        return fireStationRepository.getAllFireStation();
    }
}
