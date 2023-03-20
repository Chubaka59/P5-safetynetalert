package com.openclassrooms.safetynetalert.dto.flood;

import lombok.Data;

import java.util.List;

@Data
public class FloodDTO {
    String address;
    List<FloodPersonDTO> floodPersonDTOList;

    public FloodDTO(String address, List<FloodPersonDTO> floodPersonDTOList) {
        this.address = address;
        this.floodPersonDTOList = floodPersonDTOList;
    }
}
