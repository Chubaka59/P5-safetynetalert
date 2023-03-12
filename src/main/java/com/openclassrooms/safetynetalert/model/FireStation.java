package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class FireStation {
    @Null(groups = OnUpdate.class)
    @NotBlank
    String address;
    @NotBlank
    int station;
}
