package com.openclassrooms.safetynetalert.dto.firestation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFireStationDTO {

    @NotBlank
    private String address;
    @Min(1)
    @Max(4)
    private int station;
}
