package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class FireStation {
    @Null(groups = OnUpdate.class)
    @NotBlank
    private String address;
    @Min(1)
    private int station;
}
