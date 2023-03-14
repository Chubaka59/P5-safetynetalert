package com.openclassrooms.safetynetalert.dto;

import lombok.Data;

import java.util.List;

@Data
public class FloodFireStationDTO {
    String firstName;
    String lastName;
    String phone;
    int age;
    List<String> medication;
    List<String> allergies;
}
