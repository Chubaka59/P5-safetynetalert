package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FireStation {
    @NotEmpty
    private String address;
    @NotEmpty
    private int station;
}
