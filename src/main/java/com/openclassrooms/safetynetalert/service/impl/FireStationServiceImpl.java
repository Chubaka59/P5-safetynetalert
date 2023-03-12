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

    @Override
    public boolean add(FireStation fireStation) {
        return fireStationRepository.add(fireStation);
    }

    @Override
    public boolean delete(String address) {
        return fireStationRepository.delete(address);
    }

    @Override
    public boolean update(FireStation fireStation, String address) {
        return fireStationRepository.update(fireStation, address);
    }
}
