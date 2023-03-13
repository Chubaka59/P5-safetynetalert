package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.Max;
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
    @Min(groups = OnUpdate.class,value = 1)
    @Max(4)
    @Max(groups = OnUpdate.class, value = 4)
    private int station;
}
