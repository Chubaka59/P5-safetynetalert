package com.openclassrooms.safetynetalert.model;

import com.openclassrooms.safetynetalert.dto.firestation.CreateFireStationDTO;
import com.openclassrooms.safetynetalert.dto.firestation.UpdateFireStationDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FireStation {

    @NotBlank
    private String address;
    @Min(1)
    @Max(4)
    private int station;

    public FireStation update(UpdateFireStationDTO fireStationDTO){
        this.setStation(fireStationDTO.getStation());
        return this;
    }

    public FireStation create(CreateFireStationDTO fireStationDTO){
        this.setAddress(fireStationDTO.getAddress());
        this.setStation(fireStationDTO.getStation());
        return this;
    }
}
