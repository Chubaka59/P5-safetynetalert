package com.openclassrooms.safetynetalert.dto;

import lombok.Data;

@Data
public class FireStation {
    String firstName;
    String lastName;
    String address;
    String phone;
    int numberOfMinor;
    int numberOfMajor;
}
