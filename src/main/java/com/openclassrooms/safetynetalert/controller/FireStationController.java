package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FireStationController {

    private final FireStationService fireStationService;

    @GetMapping(value = "/firestations")
    public List<FireStation> findAllFireStations(){
        return this.fireStationService.getFireStations();
    }
}
