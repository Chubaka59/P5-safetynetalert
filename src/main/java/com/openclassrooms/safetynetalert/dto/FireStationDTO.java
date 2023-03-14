package com.openclassrooms.safetynetalert.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireStationDTO {
    List<PersonDTO> personDTOList;
    int numberOfMinor;
    int numberOfMajor;
}
