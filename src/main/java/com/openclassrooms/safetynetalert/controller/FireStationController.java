package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.FireStation;
import com.openclassrooms.safetynetalert.model.OnUpdate;
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
    public ResponseEntity<FireStation> add(@RequestBody @Validated FireStation fireStation){
        if (fireStationService.add(fireStation)) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.error("There is already a station assigned to this address");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping (value = "/firestation/{address}")
    public ResponseEntity<FireStation> delete(@PathVariable String address){
        if (fireStationService.delete(address)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping (value = "/firestation/{address}")
    public ResponseEntity<FireStation> update(@RequestBody @Validated(OnUpdate.class) FireStation fireStation, @PathVariable String address){
        if (fireStationService.update(fireStation, address)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
