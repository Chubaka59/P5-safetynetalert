package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.dto.fire.FireDTO;
import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.FireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.flood.FloodDTO;
import com.openclassrooms.safetynetalert.dto.phonealert.PhoneAlertDTO;
import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FireStationController {

    private final FireStationService fireStationService;

    @GetMapping(value = "/firestation")
    public List<FireStation> findAllFireStations(){
        return fireStationService.getFireStations();
    }

    @PostMapping (value = "/firestation")
    public ResponseEntity<FireStation> add(@RequestBody @Validated CreateFireStationDTO fireStationDTO){
        try {
            fireStationService.add(fireStationDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping (value = "/firestation/{address}")
    public ResponseEntity<FireStation> delete(@PathVariable String address) {
        try {
            fireStationService.delete(address);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping (value = "/firestation/{address}")
    public ResponseEntity<FireStation> update(@RequestBody @Validated UpdateFireStationDTO fireStation, @PathVariable String address) {
        try {
            fireStationService.update(fireStation, address);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping (value = "/firestations")
    public FireStationDTO getPersonsFromFireStation(@RequestParam int stationNumber){
        return fireStationService.getPersonsFromFireStation(stationNumber);
    }

    @GetMapping (value = "/phoneAlert")
    public List<PhoneAlertDTO> getPhoneAlertList(@RequestParam int firestation){
        return fireStationService.getPhoneAlertList(firestation);
    }

    @GetMapping (value = "/fire")
    public FireDTO getFireStationFromAddress(@RequestParam String address){
        return fireStationService.getFireStationFromAddress(address);
    }

    @GetMapping (value = "/flood/stations")
    public List<FloodDTO> getHomeListFromFireStationList(@RequestParam List<Integer> stations){
        return fireStationService.getHomeListFromFireStationList(stations);
    }
}
