package com.openclassrooms.safetynetalert.dto.fire;

import com.openclassrooms.safetynetalert.model.FireStation;
import lombok.Data;

import java.util.List;


@Data
public class FireDTO {
    Integer fireStationNumber;
    List<FirePersonDTO> firePersonDTOList;

    public FireDTO(FireStation fireStation, List<FirePersonDTO> firePersonDTOList){
        this.fireStationNumber = fireStation.getStation();
        this.firePersonDTOList = firePersonDTOList;
    }
}