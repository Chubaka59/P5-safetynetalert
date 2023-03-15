package com.openclassrooms.safetynetalert.dto.firestation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateFireStationDTO {
    @Min(1)
    @Max(4)
    private int station;
}
