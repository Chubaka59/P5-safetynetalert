package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.dto.medicalrecord.CreateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.dto.medicalrecord.UpdateMedicalRecordDTO;
import com.openclassrooms.safetynetalert.model.MedicalRecord;
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

    @GetMapping(value = "/medicalrecord")
    public List<MedicalRecord> findAllMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<MedicalRecord> add(@RequestBody @Validated CreateMedicalRecordDTO medicalRecordDTO) {
        try {
            medicalRecordService.add(medicalRecordDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/medicalrecord/{firstName}&{lastName}")
    public ResponseEntity<MedicalRecord> delete(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            medicalRecordService.delete(firstName, lastName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/medicalrecord/{firstName}&{lastName}")
    public ResponseEntity<MedicalRecord> update(@RequestBody @Validated UpdateMedicalRecordDTO medicalRecordDTO, @PathVariable String firstName, @PathVariable String lastName) {
        try {
            medicalRecordService.update(medicalRecordDTO, firstName, lastName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
