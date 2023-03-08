package com.openclassrooms.safetynetalert.controller;

import com.openclassrooms.safetynetalert.model.MedicalRecord;
import com.openclassrooms.safetynetalert.service.impl.MedicalRecordServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordController {
    MedicalRecordServiceImpl medicalRecordService = new MedicalRecordServiceImpl();

    @GetMapping(value = "/medicalrecords")
    public List<MedicalRecord> findAllMedicalRecords(){
        return medicalRecordService.getMedicalRecords();
    }
}
