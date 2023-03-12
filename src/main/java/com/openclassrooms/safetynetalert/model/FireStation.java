package com.openclassrooms.safetynetalert.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class FireStation {
    @Null(groups = OnUpdate.class)
    @NotEmpty
    String address;
    @NotEmpty
    int station;
}
