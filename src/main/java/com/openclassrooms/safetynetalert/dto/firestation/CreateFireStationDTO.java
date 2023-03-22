package com.openclassrooms.safetynetalert.dto.firestation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFireStationDTO {

    @NotBlank
    private String address;
    @Min(1)
    @Max(4)
    private int station;
}
