package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.service.FireStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FireStationController {
    FireStationService fireStationService = new FireStationService();

    @GetMapping(value = "/firestations")
    public List<FireStation> findAllFireStations(){
        return fireStationService.getFireStations();
    }
}
