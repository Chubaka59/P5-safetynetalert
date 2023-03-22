package com.openclassrooms.safetynetalert.dto.firestation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFireStationDTO {
    @Min(1)
    @Max(4)
    private int station;
}
