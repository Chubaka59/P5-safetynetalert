package com.openclassrooms.safetynetalert.dto.fire;

import lombok.Data;

import java.util.List;


@Data
public class FireDTO {
    String fireStationNumber;
    List<FirePersonDTO> firePersonDTOList;

    public FireDTO(String fireStationNumber, List<FirePersonDTO> firePersonDTOList){
        this.fireStationNumber = fireStationNumber;
        this.firePersonDTOList = firePersonDTOList;
    }
}