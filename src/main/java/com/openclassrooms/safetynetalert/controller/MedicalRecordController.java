package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.model.OnUpdate;
import com.openclassrooms.safetynetalert.service.impl.MedicalRecordServiceImpl;
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
public class MedicalRecordController {
    private final MedicalRecordServiceImpl medicalRecordService;

    @GetMapping(value = "/medicalrecords")
    public List<MedicalRecord> findAllMedicalRecords(){
        return medicalRecordService.getMedicalRecords();
    }

    @PostMapping (value = "/medicalrecords")
    public ResponseEntity<MedicalRecord> add(@RequestBody @Validated MedicalRecord medicalRecord){
        if(medicalRecordService.add(medicalRecord)){
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            log.error("MedicalRecord already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/medicalrecords/{firstName}&{lastName}")
    public ResponseEntity<MedicalRecord> delete(@PathVariable String firstName, @PathVariable String lastName){
        if(medicalRecordService.delete(firstName, lastName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping (value = "/medicalrecords/{firstName}&{lastName}")
    public ResponseEntity<MedicalRecord> update(@RequestBody @Validated(OnUpdate.class) MedicalRecord medicalRecord, @PathVariable String firstName, @PathVariable String lastName){
        if (medicalRecordService.update(medicalRecord, firstName, lastName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
