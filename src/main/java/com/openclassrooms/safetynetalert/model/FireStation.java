package com.openclassrooms.safetynetalert.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"address","station"})
public class FireStation {
    @JsonProperty("address")
    private String address;
    @JsonProperty("station")
    private int station;
}
