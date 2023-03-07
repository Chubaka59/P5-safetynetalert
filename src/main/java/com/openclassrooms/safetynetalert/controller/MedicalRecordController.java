package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.service.MedicalRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordController {
    MedicalRecordService medicalRecordService = new MedicalRecordService();

    @GetMapping(value = "/medicalrecords")
    public List<MedicalRecord> findAllMedicalRecords(){
        return medicalRecordService.getMedicalRecords();
    }
}
