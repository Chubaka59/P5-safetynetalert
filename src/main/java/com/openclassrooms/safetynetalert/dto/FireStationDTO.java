package com.openclassrooms.safetynetalert.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireStationDTO {
    List<PersonDTO> personDTOList;
    Long numberOfMinor;
    Long numberOfMajor;

    public FireStationDTO(List<PersonDTO> personDTOList, Long numberOfMinor) {
        this.personDTOList = personDTOList;
        this.numberOfMinor = numberOfMinor;
        this.numberOfMajor = personDTOList.size() - numberOfMinor;
    }
}
